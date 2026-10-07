package com.example.data

import com.example.model.AnnouncementItem
import com.example.model.Appeal
import com.example.model.CivicSurvey
import com.example.model.Hotline
import com.example.model.MunicipalDocument
import com.example.model.MunicipalFaq
import com.example.model.NewsItem
import com.example.model.OfficialPerson
import com.example.model.SocialChannel
import com.example.model.SurveyOption
import com.example.model.TaxExemption
import com.example.model.TaxType

object MunicipalDataProvider {

    val newsList = listOf(
        NewsItem(
            id = "news_1",
            title = "Kür çayı sahilboyu parkında əsaslı abadlaşdırma işləri aparılır",
            summary = "Mingəçevir şəhərinin vizit kartı sayılan sahil parkında yeni yaşıllıq zolaqları salınır, müasir işıqlandırma dirəkləri quraşdırılır.",
            content = "Mingəçevir Bələdiyyəsinin təsdiq olunmuş 2026-cı il fəaliyyət planına uyğun olaraq, Kür çayı boyunca yerləşən mərkəzi istirahət parkında genişmiqyaslı abadlıq işlərinə başlanılmışdır.\n\nLayihə çərçivəsində piyada yollarına yeni tamet daşları döşənir, uşaqlar üçün təhlükəsiz oyun meydançaları və idman qurğuları quraşdırılır. Park boyu 400-dən artıq həmişəyaşıl ağac və dekorativ gül kolları əkilmişdir.\n\nBələdiyyə sədri ərazidə görülən işlərlə tanış olaraq sakinlərin rəy və təkliflərini dinləmiş, işlərin keyfiyyətlə və vaxtında yekunlaşdırılması tapşırığını vermişdir.",
            category = "Abadlıq",
            date = "12 May 2026",
            readTime = "3 dəq",
            source = "Mingəçevir Bələdiyyəsinin Mətbuat Xidməti",
            isFeatured = true,
            viewsCount = 528,
            tags = listOf("Abadlıq", "Kür çayı", "Parklar", "Mingəçevir")
        ),
        NewsItem(
            id = "news_2",
            title = "Mingəçevir Bələdiyyə Şurasının növbəti açıq iclası keçirildi",
            summary = "İclasda rüblük büdcə icrası, yerli vergilərin toplanması və çoxmənzilli binaların həyətlərinin təmiri məsələləri müzakirə edildi.",
            content = "Bələdiyyə binasının akt zalında şəhər ictimaiyyətinin və mətbuat nümayəndələrinin iştirakı ilə Mingəçevir Bələdiyyəsinin növbəti iclası baş tutmuşdur.\n\nGündəlikdə duran əsas məsələlər: cari ilin birinci rübündə büdcə gəlir və xərclərinin hesabatı, yerli vergi və ödənişlərin şəffaflığının təmin edilməsi, habelə vətəndaşlardan daxil olan kollektiv müraciətlərin icra vəziyyəti olmuşdur.\n\nİclasın yekununda qaldırılan məsələlər üzrə müvafiq qərarlar qəbul edilmiş və aidiyyəti komissiyalara tapşırıqlar verilmişdir.",
            category = "İclaslar",
            date = "08 May 2026",
            readTime = "4 dəq",
            source = "Bələdiyyə Aparatı və Katiblik",
            isFeatured = false,
            viewsCount = 312,
            tags = listOf("Bələdiyyə Şurası", "İclas", "Büdcə", "Şəffaflıq")
        ),
        NewsItem(
            id = "news_3",
            title = "Mikrorayonlarda məhəllədaxili yolların asfaltlanması davam edir",
            summary = "Sakinlərin müraciətləri əsasında 3-cü və 4-cü mikrorayonlarda yol infrastrukturu yenilənir.",
            content = "Mingəçevir şəhərinin mikrorayonlarında vətəndaşların rahat gediş-gəlişini təmin etmək məqsədilə daxili yollara yeni asfalt örtüyü salınır.\n\nİşlər ilkin olaraq ən çox zədələnmiş küçələrdə aparılır. Eyni zamanda yağış sularının axıdılması üçün drenaj xətləri təmizlənir və yeni suötürücülər quraşdırılır.",
            category = "İnfrastruktur",
            date = "02 May 2026",
            readTime = "2 dəq",
            source = "Memarlıq və Kommunal Təsərrüfat Şöbəsi",
            isFeatured = false,
            viewsCount = 445,
            tags = listOf("Yol infrastrukturu", "Mikrorayonlar", "Asfaltlanma")
        ),
        NewsItem(
            id = "news_4",
            title = "Məktəblilər və gənclər arasında 'Təmiz Mingəçevir' ekoloji aksiyası",
            summary = "Bələdiyyənin təşkilatçılığı ilə şəhər gəncləri tullantıların çeşidlənməsi və yaşıllıqların qorunması aksiyasında iştirak ediblər.",
            content = "Mingəçevir Bələdiyyəsinin Ekologiya və Sosial Tədbirlər komissiyası 'Təmiz Şəhər, Yaşıl Gələcək' devizi altında aksiya təşkil etmişdir.\n\nTədbirdə 150-dən çox könüllü gənc iştirak etmiş, Mingəçevir Su Anbarı və Kür sahili zolağında təmizlik işləri həyata keçirilmişdir. Fəal iştirakçılara bələdiyyə tərəfindən təşəkkürnamələr təqdim olunmuşdur.",
            category = "Sosial",
            date = "28 Aprel 2026",
            readTime = "3 dəq",
            source = "Sosial-Humanitar Komissiya",
            isFeatured = false,
            viewsCount = 289,
            tags = listOf("Ekologiya", "Gənclər", "Təmizlik aksiyası")
        )
    )

    val announcementsList = listOf(
        AnnouncementItem(
            id = "ann_1",
            title = "Bələdiyyə mülkiyyətində olan qeyri-yaşayış sahəsinin icarəyə verilməsi üzrə açıq hərrac",
            description = "İslamzadə küçəsində yerləşən 45 kv.m sahənin icarəsi üçün müsabiqə elan olunur.",
            fullText = "Mingəçevir Bələdiyyəsi tərəfindən bələdiyyə mülkiyyətində olan qeyri-yaşayış sahəsinin sahibkarlıq fəaliyyəti məqsədilə icarəyə verilməsi üçün açıq hərrac elan edilir.\n\nHərracda iştirak etmək istəyən hüquqi və fiziki şəxslər tələb olunan sənədləri Mingəçevir Bələdiyyəsinin İqtisadi İnkişaf və Əmlak şöbəsinə təqdim edə bilərlər.",
            date = "05 May 2026",
            deadline = "25 May 2026",
            status = "Aktiv",
            department = "İqtisadi İnkişaf və Əmlak Şöbəsi",
            category = "Hərrac",
            referenceNumber = "MNG-HR-2026/04",
            attachments = listOf("Hərrac_Qaydaları.pdf", "Əmlak_Xarakteristikası.pdf")
        ),
        AnnouncementItem(
            id = "ann_2",
            title = "Vətəndaşların qəbulu: Bələdiyyə Sədrinin səyyar görüşü",
            description = "Şəhərin Energetiklər qəsəbəsi sakinlərinin müraciətlərinin yerində dinlənilməsi məqsədilə səyyar qəbul keçiriləcək.",
            fullText = "Mingəçevir Bələdiyyəsinin sədri və məsul əməkdaşları Energetiklər qəsəbəsinin sakinləri ilə görüşəcək. Görüşdə abadlıq, su-kanalizasiya, işıqlandırma və sosial sahədəki qayğılar dinləniləcək və qeydiyyata alınacaqdır.",
            date = "01 May 2026",
            deadline = "15 May 2026",
            status = "Aktiv",
            department = "Rəhbərlik və Katiblik",
            category = "İctimai Dinləmə",
            referenceNumber = "MNG-SG-2026/02",
            attachments = listOf("Görüş_Cədvəli.pdf")
        ),
        AnnouncementItem(
            id = "ann_3",
            title = "Bələdiyyə qulluğuna qəbul üzrə sənəd qəbulu yekunlaşmışdır",
            description = "Hüquq və kadr məsələləri üzrə mütəxəssis vəzifəsinə müsabiqənin birinci mərhələsi başa çatmışdır.",
            fullText = "Elan olunmuş vakant bələdiyyə qulluğu vəzifəsi üzrə sənəd qəbulu başa çatmışdır. Test və müsahibə mərhələsinin vaxtı barədə namizədlərə elektron poçt və SMS vasitəsilə məlumat göndəriləcəkdir.",
            date = "20 Aprel 2026",
            deadline = "30 Aprel 2026",
            status = "Başa çatıb",
            department = "Kadr və Hüquq Şöbəsi",
            category = "Müsabiqə",
            referenceNumber = "MNG-VK-2026/01",
            attachments = listOf("Namizədlərin_Siyahısı.pdf")
        )
    )

    val hotlinesList = listOf(
        Hotline(
            id = "hotline_muni",
            name = "Mingəçevir Bələdiyyəsi Qaynar Xətti",
            number = "(024) 274-12-34",
            description = "Vətəndaş müraciətləri, şikayət və təkliflər üçün rəsmi nömrə",
            category = "Bələdiyyə",
            isEmergency = false,
            workingHours = "İş günləri: 09:00 - 18:00"
        ),
        Hotline(
            id = "hotline_112",
            name = "112 FHN Qaynar Xətti",
            number = "112",
            description = "Fövqəladə Hallar Nazirliyinin vahid böhran idarəetmə xidməti",
            category = "Fövqəladə",
            isEmergency = true,
            workingHours = "24/7 fasiləsiz"
        ),
        Hotline(
            id = "hotline_102",
            name = "102 Şəhər Polis Şöbəsi",
            number = "102",
            description = "İctimai asayiş və təhlükəsizlik məsələləri üzrə xidmət",
            category = "Fövqəladə",
            isEmergency = true,
            workingHours = "24/7 fasiləsiz"
        ),
        Hotline(
            id = "hotline_103",
            name = "103 Təcili Tibbi Yardım",
            number = "103",
            description = "Gecə-gündüz fəaliyyət göstərən təcili və təxirəsalınmaz tibbi yardım",
            category = "Fövqəladə",
            isEmergency = true,
            workingHours = "24/7 fasiləsiz"
        ),
        Hotline(
            id = "hotline_104",
            name = "104 Qaz Qəza Xidməti",
            number = "104",
            description = "Azəriqaz İB Mingəçevir Qaz İstismarı Sahəsi qəza xidməti",
            category = "Kommunal",
            isEmergency = true,
            workingHours = "24/7 fasiləsiz"
        ),
        Hotline(
            id = "hotline_199",
            name = "199 Azərsu Qaynar Xətti",
            number = "199",
            description = "Mingəçevir Sukanal İdarəsi - içməli su və kanalizasiya xətləri",
            category = "Kommunal",
            isEmergency = false,
            workingHours = "24/7 fasiləsiz"
        ),
        Hotline(
            id = "hotline_193",
            name = "193 Azərişıq Qaynar Xətti",
            number = "193",
            description = "Mingəçevir Elektrik Şəbəkəsi - işıq fasilələri və qəza halları",
            category = "Kommunal",
            isEmergency = false,
            workingHours = "24/7 fasiləsiz"
        )
    )

    val officialsList = listOf(
        OfficialPerson(
            name = "Rəhim Məmmədov",
            position = "Mingəçevir Bələdiyyəsinin Sədri",
            department = "Rəhbərlik",
            receptionHours = "Hər həftənin Çərşənbə axşamı: 10:00 - 13:00",
            phone = "(024) 274-12-34",
            email = "sedr@mingecevir-belediyyesi.gov.az",
            roomNumber = "Otaq 201"
        ),
        OfficialPerson(
            name = "Sevinc Əliyeva",
            position = "Bələdiyyə Sədrinin Müavini",
            department = "Sosial və Humanitar Məsələlər",
            receptionHours = "Hər həftənin Cümə axşamı: 14:00 - 17:00",
            phone = "(024) 274-15-20",
            email = "muavin@mingecevir-belediyyesi.gov.az",
            roomNumber = "Otaq 204"
        ),
        OfficialPerson(
            name = "Vüqar İsmayılov",
            position = "Bələdiyyə Aparatının Rəhbəri",
            department = "Aparat və Ümumi Şöbə",
            receptionHours = "Bazar ertəsi - Cümə: 09:00 - 18:00",
            phone = "(024) 274-18-90",
            email = "aparat@mingecevir-belediyyesi.gov.az",
            roomNumber = "Otaq 105"
        )
    )

    val taxTypes = listOf(
        TaxType(
            id = "tax_property",
            name = "Fiziki şəxslərin əmlak vergisi",
            description = "Mingəçevir şəhəri ərazisində mülkiyyətdə olan yaşayış və qeyri-yaşayış binalarına tətbiq olunur.",
            legalBasis = "Azərbaycan Respublikası Vergi Məcəlləsi, Maddə 197-200",
            calculationGuide = "Yaşayış sahəsinin 30 kv.m-dən artıq olan hissəsi üçün hər kv.m-ə görə 0.20 AZN baza dərəcəsi tətbiq edilir.",
            paymentPeriod = "İldə iki dəfə bərabər hissələrlə: 15 avqust və 15 noyabr tarixlərinədək"
        ),
        TaxType(
            id = "tax_land",
            name = "Torpaq vergisi",
            description = "Fiziki şəxslərin xüsusi mülkiyyətində və ya istifadəsində olan torpaq sahələrinə hesablanır.",
            legalBasis = "Azərbaycan Respublikası Vergi Məcəlləsi, Maddə 203-207",
            calculationGuide = "Təyinatına (həyətyanı, bağ təsərrüfatı, kommersiya) uyğun olaraq müəyyən edilmiş təsdiqlənmiş dərəcələr üzrə hesablanır.",
            paymentPeriod = "İldə iki dəfə bərabər hissələrlə: 15 avqust və 15 noyabr tarixlərinədək"
        ),
        TaxType(
            id = "tax_hotel_ad",
            name = "Yerli ödənişlər (Reklam və Mehmanxana rüsumları)",
            description = "Bələdiyyə ərazisində yerləşdirilən açıq məkandakı reklamlar və mehmanxana xidmətləri üçün nəzərdə tutulan yerli ödənişlərdir.",
            legalBasis = "Yerli (bələdiyyə) vergilər və ödənişlər haqqında AR Qanunu",
            calculationGuide = "Reklam qurğusunun ölçüsü və təyinatına uyğun tarif cədvəlinə əsasən bələdiyyə büdcəsinə ödənilir.",
            paymentPeriod = "Xidmətin həyata keçirildiyi aydan sonrakı ayın 20-dən gec olmayaraq"
        )
    )

    val taxExemptionsList = listOf(
        TaxExemption(
            id = "ex_1",
            title = "30 m² Yaşayış Sahəsi Güzəşti",
            description = "Bütün fiziki şəxslərin xüsusi mülkiyyətində olan yaşayış binalarının və mənzillərin 30 kvadratmetrədək olan sahəsi əmlak vergisindən tam azaddır.",
            legalArticle = "AR Vergi Məcəlləsi, Maddə 198.1.1"
        ),
        TaxExemption(
            id = "ex_2",
            title = "Şəhid ailələri və Vətən Müharibəsi İştirakçıları",
            description = "Azərbaycan Respublikasının suverenliyi uğrunda həlak olanların ailə üzvləri və müharibə veteranları üçün qanunvericiliklə müəyyən edilmiş vergi güzəştləri tətbiq edilir.",
            legalArticle = "AR Vergi Məcəlləsi, Maddə 199.3"
        ),
        TaxExemption(
            id = "ex_3",
            title = "I və II dərəcə əlilliyi olan şəxslər",
            description = "Əlilliyi olan şəxslərin mülkiyyətində olan binalar üzrə əmlak vergisinin məbləği 30 manatadək azaldılır.",
            legalArticle = "AR Vergi Məcəlləsi, Maddə 199.4"
        )
    )

    val taxFaqsList = listOf(
        MunicipalFaq(
            id = "faq_tax_1",
            question = "Bələdiyyə vergi borcumu necə öyrənə bilərəm?",
            answer = "Bələdiyyə vergi borcunuzu Hökumət Ödəniş Portalı (gpp.az) üzərindən FİN kodunuzla və ya Mingəçevir Bələdiyyəsinin İqtisadi İnkişaf şöbəsinə yaxınlaşaraq öyrənə bilərsiniz.",
            category = "Vergilər"
        ),
        MunicipalFaq(
            id = "faq_tax_2",
            question = "Vergini vaxtında ödəmədikdə nə baş verir?",
            answer = "Qanunvericiliyə uyğun olaraq, müəyyən edilmiş müddətdən (15 avqust və 15 noyabr) sonra ödənilməyən hər gecikdirilmiş gün üçün 0.05% faiz hesablanır.",
            category = "Vergilər"
        )
    )

    val appealsFaqsList = listOf(
        MunicipalFaq(
            id = "faq_app_1",
            question = "Müraciətimə nə qədər müddət ərzində baxılacaq?",
            answer = "'Vətəndaşların müraciətləri haqqında' AR Qanununun 10-cu maddəsinə əsasən, müraciətlərə ən geci 15 iş günü, əlavə araşdırma tələb edildikdə isə 30 iş günü ərzində baxılır.",
            category = "Müraciətlər"
        ),
        MunicipalFaq(
            id = "faq_app_2",
            question = "Anonim müraciət göndərmək mümkündürmü?",
            answer = "Qanuna əsasən, müraciət edən şəxsin adı, soyadı, ünvanı və əlaqə nömrəsi qeyd olunmayan anonim müraciətlərə baxılmır.",
            category = "Müraciətlər"
        )
    )

    val socialChannels = listOf(
        SocialChannel(
            name = "Facebook",
            handle = "@MingecevirBelediyyesiOfficial",
            url = "https://facebook.com",
            iconType = "facebook",
            description = "Gündəlik fəaliyyət hesabatları, foto və video icmallar"
        ),
        SocialChannel(
            name = "Instagram",
            handle = "@mingecevir_belediyyesi",
            url = "https://instagram.com",
            iconType = "instagram",
            description = "Şəhər həyatından görüntülər, layihələr və elanlar"
        ),
        SocialChannel(
            name = "Telegram",
            handle = "@mingecevir_belediyye_xeber",
            url = "https://t.me",
            iconType = "telegram",
            description = "Operativ bildirişlər və rəsmi şəhər xəbərdarlıqları"
        ),
        SocialChannel(
            name = "YouTube",
            handle = "Mingəçevir Bələdiyyəsi TV",
            url = "https://youtube.com",
            iconType = "youtube",
            description = "Bələdiyyə iclaslarının video yazı və süjetləri"
        )
    )

    val initialAppeals = listOf(
        Appeal(
            id = "app_1",
            trackingCode = "MNG-2026-8412",
            applicantName = "Əli Qasımov",
            finCode = "5KZ987A",
            phone = "+994 50 312 44 55",
            address = "N.Nərimanov küçəsi, bina 14, m. 22",
            category = "Abadlaşdırma",
            subject = "Həyətyanı sahədə işıqlandırma dirəyinin təmiri",
            description = "Binamızın həyətindəki 2 ədəd gecə işıqlandırma lampası yanmır, axşam saatlarında qaranlıq olur. Zəhmət olmasa təmir olunmasına köməklik göstərəsiniz.",
            date = "04 May 2026",
            status = "İcradadır",
            response = "Müraciətiniz qeydiyyata alınıb və Mingəçevir Elektrik Şəbəkəsi ilə birgə yerində baxış keçirilmişdir. Cari həftə ərzində lampalar dəyişdiriləcəkdir."
        ),
        Appeal(
            id = "app_2",
            trackingCode = "MNG-2026-6190",
            applicantName = "Nigar Həsənova",
            finCode = "6LX412B",
            phone = "+994 55 889 12 30",
            address = "Heydər Əliyev prospekti, bina 8",
            category = "Məişət tullantıları",
            subject = "Yeni zibil qutularının yerləşdirilməsi",
            description = "Məhəlləmizdə tullantı konteynerlərinin sayı azdır, xahiş edirik əlavə konteyner ayrılsın.",
            date = "29 Aprel 2026",
            status = "Tamamlandı",
            response = "Müraciətiniz əsasında əraziyə 2 ədəd yeni plastik tullantı konteyneri quraşdırılmışdır. Bələdiyyəyə müraciətiniz üçün təşəkkür edirik."
        )
    )

    val documentsList = listOf(
        MunicipalDocument(
            id = "doc_1",
            title = "Mingəçevir Bələdiyyəsinin Nizamnaməsi",
            category = "Əsasnamə və Nizamnamələr",
            date = "2024",
            fileType = "PDF",
            size = "1.8 MB",
            description = "Bələdiyyənin hüquqi əsaslarını, səlahiyyətlərini və vətəndaşlarla qarşılıqlı münasibətlərini tənzimləyən ali bələdiyyə sənədi."
        ),
        MunicipalDocument(
            id = "doc_2",
            title = "2026-cı il Bələdiyyə Büdcəsinin Gəlir və Xərclər Smeytası",
            category = "Maliyyə və Hesabatlar",
            date = "Yanvar 2026",
            fileType = "PDF",
            size = "3.2 MB",
            description = "Cari il üçün yerli büdcə gəlirlərinin mənbələri və şəhər abadlığına ayrılan xərclərin təsdiq olunmuş smetası."
        ),
        MunicipalDocument(
            id = "doc_3",
            title = "Mingəçevir şəhərində Abadlıq və Sanitariya Qaydaları",
            category = "Qaydalar və Təlimatlar",
            date = "Mart 2025",
            fileType = "PDF",
            size = "950 KB",
            description = "Şəhər ərazisində məişət tullantılarının toplanması, yaşıllıqların qorunması və binaların estetik görünüşü üzrə məcburi qaydalar."
        )
    )

    val defaultSurvey = CivicSurvey(
        id = "survey_2026_1",
        title = "2026-cı ildə şəhərimizdə hansı layihəyə üstünlük verilməlidir?",
        description = "Sakinlərimizin səsverməsi bələdiyyə büdcəsinin ayrılmasında nəzərə alınacaqdır.",
        options = listOf(
            SurveyOption(id = "opt_1", text = "Məhəllədaxili yolların əsaslı təmiri", votes = 642),
            SurveyOption(id = "opt_2", text = "Kür sahili boyunca yeni velosiped zolaqları", votes = 489),
            SurveyOption(id = "opt_3", text = "Uşaqlar və gənclər üçün açıq idman meydançaları", votes = 512),
            SurveyOption(id = "opt_4", text = "Şəhər parklarında geniş yaşıllaşdırma və ağacəkmə", votes = 378)
        ),
        totalVotes = 2021
    )
}
