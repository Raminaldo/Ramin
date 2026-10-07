const {
  initializeTestEnvironment,
  assertFails,
  assertSucceeds,
} = require("@firebase/rules-unit-testing");
const { test, before, after, beforeEach } = require("node:test");
const fs = require("node:fs");

let testEnv;
const PROJECT_ID = "demo-no-project";
const ADMIN_UID = "admin_user_1";
const ADMIN_EMAIL = "ehmedovramin646@gmail.com";
const USER_UID = "regular_user_1";

const [emulatorHost, emulatorPortStr] = (process.env.FIRESTORE_EMULATOR_HOST || "127.0.0.1:8085").split(":");
const emulatorPort = parseInt(emulatorPortStr, 10);

before(async () => {
  const rules = fs.readFileSync("./firestore.rules", "utf8");
  testEnv = await initializeTestEnvironment({
    projectId: PROJECT_ID,
    firestore: {
      rules,
      host: emulatorHost,
      port: emulatorPort,
    },
  });
});

after(async () => {
  if (testEnv) {
    await testEnv.cleanup();
  }
});

beforeEach(async () => {
  if (testEnv) {
    await testEnv.clearFirestore();
  }
});

test("Public user can read news and hotlines", async () => {
  const unauthDb = testEnv.unauthenticatedContext().firestore();
  await assertSucceeds(unauthDb.collection("news").get());
  await assertSucceeds(unauthDb.collection("hotlines").get());
  await assertSucceeds(unauthDb.collection("announcements").get());
});

test("Unauthenticated user cannot create news or hotlines", async () => {
  const unauthDb = testEnv.unauthenticatedContext().firestore();
  await assertFails(unauthDb.collection("news").doc("news1").set({
    id: "news1",
    title: "Test",
    summary: "Test",
    content: "Test",
    category: "General",
    date: "07.10.2026",
    isActive: true
  }));
});

test("Regular user (non-admin) cannot create or update news", async () => {
  const userDb = testEnv.authenticatedContext(USER_UID, { email: "user@example.com" }).firestore();
  await assertFails(userDb.collection("news").doc("news1").set({
    id: "news1",
    title: "Unauthorized News",
    summary: "Summary",
    content: "Content",
    category: "General",
    date: "07.10.2026",
    isActive: true
  }));
});

test("Admin user (ehmedovramin646@gmail.com) can create news, hotlines, announcements", async () => {
  const adminDb = testEnv.authenticatedContext(ADMIN_UID, { email: ADMIN_EMAIL }).firestore();
  
  await assertSucceeds(adminDb.collection("news").doc("news1").set({
    id: "news1",
    title: "Yeni Park Salınır",
    summary: "Mingəçevirdə yeni istirahət parkı istifadəyə verilir.",
    content: "Ətraflı məlumat mətni burada yerləşir...",
    category: "Abadlıq",
    date: "07 Oktyabr 2026",
    isActive: true
  }));

  await assertSucceeds(adminDb.collection("hotlines").doc("hotline1").set({
    id: "hotline1",
    title: "Bələdiyyə Qaynar Xətti",
    number: "164",
    description: "24/7 rejimdə fəaliyyət göstərir",
    category: "Bələdiyyə",
    order: 1,
    isActive: true
  }));

  await assertSucceeds(adminDb.collection("announcements").doc("ann1").set({
    id: "ann1",
    title: "İctimai Dinləmə",
    description: "Şəhər büdcəsi üzrə ictimai müzakirə təşkil olunacaq.",
    date: "15 Oktyabr 2026",
    type: "İctimai Dinləmə",
    priority: "Yüksək",
    isActive: true
  }));
});

test("Regular user cannot access admin accounts list", async () => {
  const userDb = testEnv.authenticatedContext(USER_UID, { email: "user@example.com" }).firestore();
  await assertFails(userDb.collection("admins").get());
});

test("Primary admin can create their admin document, but regular user cannot", async () => {
  const adminDb = testEnv.authenticatedContext(ADMIN_UID, { email: ADMIN_EMAIL }).firestore();
  
  // Primary admin creates their admin document
  await assertSucceeds(adminDb.collection("admins").doc(ADMIN_UID).set({
    uid: ADMIN_UID,
    email: ADMIN_EMAIL,
    role: "admin"
  }));

  // Regular non-admin user CANNOT create an admin document for themselves
  const regularDb = testEnv.authenticatedContext(USER_UID, { email: "user@example.com" }).firestore();
  await assertFails(regularDb.collection("admins").doc(USER_UID).set({
    uid: USER_UID,
    email: "user@example.com",
    role: "admin"
  }));

  // Regular non-admin user CANNOT create an admin document for another user
  await assertFails(regularDb.collection("admins").doc("other_user").set({
    uid: "other_user",
    email: "other@example.com",
    role: "admin"
  }));
});

test("Admin with document in /admins/{uid} can perform admin operations", async () => {
  // Primary admin registers a new admin
  const primaryAdminDb = testEnv.authenticatedContext(ADMIN_UID, { email: ADMIN_EMAIL }).firestore();
  const delegatedAdminUid = "delegated_admin_42";
  await assertSucceeds(primaryAdminDb.collection("admins").doc(delegatedAdminUid).set({
    uid: delegatedAdminUid,
    email: "deputy@mingecevir.gov.az",
    role: "admin"
  }));

  // Delegated admin can now create news
  const delegatedDb = testEnv.authenticatedContext(delegatedAdminUid, { email: "deputy@mingecevir.gov.az" }).firestore();
  await assertSucceeds(delegatedDb.collection("news").doc("news_delegated").set({
    id: "news_delegated",
    title: "Müavin tərəfindən xəbər",
    summary: "Xülasə mətni",
    content: "Ətraflı məzmun mətni...",
    category: "Bələdiyyə",
    date: "07 Oktyabr 2026",
    isActive: true
  }));
});
