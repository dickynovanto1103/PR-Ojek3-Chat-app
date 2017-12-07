# Tugas 2 IF3110 Pengembangan Aplikasi Berbasis Web

Melakukan *upgrade* Website ojek online sederhana pada Tugas 1 dengan mengaplikasikan **arsitektur web service REST dan SOAP**.

## Deskripsi singkat sistem

> SOAP (Simple Object Access Protocol) adalah sebuah protokol untuk mendukung komunikasi antaraplikasi. Protokol ini memungkinkan sebuah aplikasi dalam sebuah mesin dapat memanggil fungsi atau prosedur atau lainnya dari aplikasi lain yang mungkin bahkan berada di dalam mesin lain. Untuk web yang kami kembangkan pada tugas ini, SOAP digunakan dalam Add Produce, Order, History, dan Profile. Webservice perlu mengeksekusi fungsionalitas operasional web, misalnya memesan driver, menampilkan riwayat ojek, atau mengakses profile. Fungsi-fungsi tersebut juga perlu melakukan akses ke informasi user yang tersimpan pada identity service. Kemudian hasilnya akan digunakan pada JSP. SOAP memungkinkan adanya komunikasi antara JSP, identity service, dan webservice yang berada pada port yang berbeda.

> REST (REpresentational State Transfer) merupakan standar arsitektur komunikasi berbasis web yang sering diterapkan dalam pengembangan layanan berbasis web. Umumnya menggunakan HTTP (Hypertext Transfer Protocol) sebagai protocol untuk komunikasi data.

![Gambar Arsitektur Umum](arsitektur_umum.png)

## Prerequisite

[IntelliJ IDEA](https://www.jetbrains.com/help/idea/installing-and-launching.html) 

[Jetty](https://plugins.jetbrains.com/plugin/7505-idea-jetty-runner)

[Java SE Development Kit 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)

[MySQL Connector/J](https://dev.mysql.com/downloads/connector/j/5.1.html)


## Anggota Tim

* 13515002 - Wenny Yustalim
* 13515053 - Yohanes Jhouma Parulian
* 13515071 - Daniel Pintara

## Penjelasan
1. Basis data sistem.

> Basis data yang kami gunakan hampir sama dengan basis data pada tugas sebelumnya. Kami memecah basis data pada tugas 1 menjadi 2 buah basis data, yaitu basis data untuk Identity Service dan basis data untuk Web Service. Basis data Web Service mencakup hampir semua tabel, hanya dikurangi dengan table username dan password karena kedua hal tersebut akan disimpan di basis data Identity Service.

Basis data Identity Service berisi:
* accounts: {id, username, password}
* tokens: {token, account_id, expire_date}

Basis data Web Service berisi:
* locations: {id, driver_id, name}
* transactions: {id, picking_point, destination, driver_id, user_id, rating, comment, order_date, show_user_history, show_driver_history}
* users: {id, full_name, email, phone_number, is_driver}
* popularities (view): {driver_id, rating, vote_count}

2. Konsep *shared session* dengan menggunakan REST.

> Kami memanfaatkan cookie untuk mengimplementasikan shared session. Ketika user melakukan register yang valid dan login yang valid, maka token beserta expiry time nya akan digenerate oleh IdentityService dan disimpan di basis data. Dalam pemrosesan setiap servlet, akan dicek apakah cookie tersebut sudah ada, jika sudah maka user tidak perlu melakukan login lagi dan bisa langsung mengakses halaman tersebut. Jika cookie sudah expired atau cookie tidak ditemukan maka servlet akan meredirect user kembali ke halaman login untuk melakukan login. Saat user logout, maka cookie akan dihapus. Cookie yang digenerate berdasarkan token.

3. Pembangkitan token dan expire time pada sistem.

> Token dibangkitkan ketika user telah berhasil register atau telah berhasil login. Expiry time yang kami atur adalah satu minggu. Alasan pemilihan waktu satu minggu adalah agar ketika user ingin memesan ojek tidak usah login, tetapi kami juga tidak ingin overloaded by tokens. 

4. Kelebihan dan kelemahan dari arsitektur aplikasi tugas ini, dibandingkan dengan aplikasi monolitik (login, CRUD DB, dll jadi dalam satu aplikasi)

>   Kelebihan dari arsitektur aplikasi SOAP/REST dibandingkan dengan aplikasi monolitik adalah:
  > * Pengembangan dapat dilakukan secara modular.
  > * Kesalahan pada satu bagian sistem tidak menyebabkan kesalahan pada bagian lainnya.
  > * Desain sistem scalable, berarti ketika aplikasi berkembang sistem login dapat digunakan meskipun server lebih dari 1 karena sesi tidak disimpan dari server tapi di dalam browser pengguna dalam bentuk cookie.
  > * Jika sistem ingin dikembangkan dengan menambah service baru, sistem registrasi dan autentikasi bisa menggunakan sistem yang sudah ada, seperti konsep SSO (Single Sign On).
  
> Kelemahan dari arsitektur aplikasi SOAP/REST dibandingkan dengan aplikasi monolitik adalah:
  > * Layanan yang dilakukan akan membutuhkan waktu lebih lama dikarenakan harus melalui pertukaran pesan antara service di backend.
  

## Bonus
Mekanisme *auto-renew* access token yang tepat sehingga user tidak ter-logout secara paksa saat melakukan serangkaian aktivitas pada sistem dalam waktu yang cukup lama. Access token dapat di generate kembali ketika lifetime dari token tersebut habis. Cara implementasi dibebaskan.

## Pembagian Tugas
### REST
1. Generate token: 13515071
2. Validasi token: 13515071
3. Account: 13515053
4. DataService: 13515071

### SOAP
1. User: 13515002
2. Transaction: 13515053
3. Popularity: 13515053
4. Location: 13515053
5. DataService: 13515071

### Web app (JSP)
1. Halaman login: 13515071
2. Halaman register: 13515071
3. Halaman profile: 13515002
4. Halaman edit profile: 13515002
5. Halaman select destination: 13515053
6. Halaman select driver: 13515053
7. Halaman komentar: 13515002
8. Halaman history: 13515002
9. Halaman edit: 13515002

## About
* Special thanks to
  * Bapak Yudistira Dwi Wardhana Asnar ST,Ph.D.
  * Bapak Riza Satria Perdana ST,MT.
  * Bapak Dr.Techn. Muhammad Zuhri Catur Candra ST,MT
  for being such admirable lecturers.

* Also, our dearest Asisten IF3110 2017
  * Ade
  * Johan
  * Kristianto
  * Micky
  * Michael
  * Rangga
  * Raudi
  * Robert
  * Sashi
  for the time and energy spent on handling our assignments.
