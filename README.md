# GitHub Trend Analiz Projesi

## Genel Bakış
Bu proje, **Java**, **Spring Boot** ve **Neo4j** kullanarak geliştirilmiş bir **GitHub Trend Analiz** uygulamasıdır. Projenin amacı, GitHub depolarından en çok yıldızlanan depolar, katkıda bulunanlar, dil veya konu bazlı trendleri analiz etmek ve görselleştirmektir.

## Proje Yapısı
Proje, modülerliği ve sürdürülebilirliği sağlamak amacıyla katmanlı bir mimari kullanmaktadır. Temel katmanlar şunlardır:

- **config**: Spring Boot, Neo4j ve diğer gerekli yapılandırmaları içeren dosyalar.
- **controller**: HTTP isteklerini karşılayan ve ilgili servislerle eşleşen denetleyiciler.
- **models**: Neo4j graf veritabanında kullanılan veriyi temsil eden varlık sınıfları.
- **objects**: Katmanlar arasında kompleks veri transferi için kullanılan özel veri nesneleri.
- **queryresults**: Karmaşık Neo4j sorgularının sonuçlarını yakalamak için kullanılan sınıflar.
- **repositories**: `Neo4jRepository`'yi genişleten ve CRUD işlemlerini gerçekleştiren arayüzler.
- **requests**: API'den gelen istek verilerini işleyen sınıflar.
- **services**: İstekleri işleyen ve veritabanı ile etkileşim kuran iş mantığı katmanı.

## Özellikler
- GitHub depolarını analiz eder ve trend verilerini sunar.
- Yıldız, fork, dil,lisans,konu vb. bazlı trend analizleri.
- Neo4j entegrasyonu ile grafik tabanlı veri depolama ve sorgulama.
- REST API üzerinden trend verilerine erişim.
- Servis tabanlı modüler mimari.

## Kullanılan Teknolojiler
- **Java**: Uygulamanın temel programlama dili.
- **Spring Boot**: RESTful web servislerinin geliştirilmesi için kullanılan backend framework.
- **Neo4j**: Depolar, geliştiriciler ve trendler arasındaki ilişkileri saklamak için kullanılan graf veritabanı.
- **Maven**: Bağımlılık yönetimi.
- **GitHub API**: GitHub ile ilgili verileri almak için kullanılan API.

## Kurulum Adımları

### Gereksinimler
- Java 17+
- Maven
- Neo4j Veritabanı (yerel veya bulut tabanlı)
- GitHub API Token (veri çekmek için)

### Kurulum
1. **Projenin klonlanması**:
    ```bash
    git clone https://github.com/kullanici-adi/github-trend-analiz.git
    cd github-trend-analiz
    ```

2. **Uygulamanın yapılandırılması**:
   `application.properties` dosyasını Neo4j bağlantı bilgileri ve GitHub API token'ınız ile güncelleyin.

3. **Uygulamayı çalıştırma**:
    ```bash
    mvn spring-boot:run
    ```

4. **API'ye erişim**:
   Sunucu çalıştığında, aşağıdaki adreslerden API'yi kullanabilirsiniz:
   - `http://localhost:8080/api/repo/fetch-and-save?startDate=2021-01-01&endDate=2022-01-01`
   - `http://localhost:8080/api/repo/createat/range?startDate=2023-01-01&endDate=2024-01-01`
   - `http://localhost:8080/api/v1/page?page=0&size=1`
   - `http://localhost:8080/api/v1/count-by-topic`
   - `http://localhost:8080/api/v1/topic/{topic}`
   - `http://localhost:8080/api/v1/repo-counts`
   - `http://localhost:8080/api/v1/language/{languageName}`
   - `http://localhost:8080/api/v1/repo-counts-license`
   

### Örnek API Endpointleri
- **GET /api/repo/fetch-and-save?startDate=2021-01-01&endDate=2022-01-01**: 2021-2022 yılları arasında oluşturulan repoları yıldız ve fork sayılarına göre azalan sırada sıralar ve ilk 3 sayfadaki verileri Neo4j veri tabanına kayıt eder.
- **GET /api/repo/createat/range?startDate=2023-01-01&endDate=2024-01-01**: Veri tabanına kayıt edilmiş kayıtlardan 2023-2024 yılı arasında oluşturulmuş ve en çok yıldız,fork sayısına sahip kayıtları görüntüler.
- **GET /api/v1/page?page=2&size**=20: Tüm verileri sayfalama yaparak getirir.Bir sayfada 20 kayıt vardır.Ve 2.sayfadaki kayıtlar gösterilir.(Sayfa numaralandırması 0 dan başlar.)
- **GET /api/v1/count-by-topic**: Konuya göre repo sayısını döndürür.
- **GET /api/v1/topic/{topic}**: Girilen konuya göre repoları döndürür.
- **GET /api/v1/repo-counts**: Dile göre repo sayısını döndürür.
- **GET /api/v1/language/{languageName}**:Girilen dile dile göre repoları döndürür.
- **Get /api/v1/repo-counts-license**: Lisanslara göre repo sayısını döndürür.
## Çalışma Prensibi
1. **Controller** katmanı, HTTP isteklerini karşılar ve ilgili servis çağrılarını yapar.
2. **Services** katmanı, iş mantığını işler, GitHub API ile etkileşime geçer ve **Neo4j veritabanı** ile veri alışverişi yapar.
3. **Repositories** arayüzü, `Neo4jRepository`'yi genişleterek veritabanı ile etkileşim kurar ve veriyi hızlı bir şekilde saklar veya sorgular.
4. **QueryResults** sınıfları, özel Neo4j sorgularının sonuçlarını yakalar ve kontrolcüler üzerinden istemcilere döner.

## Projeyi Yapanlar
- **Miray Tepe**
- **Meryem Zeynep Altunal**
- **Esra Altınay**


