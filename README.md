# PR-Ojek3-Chat-app-
Adding functionality to PR-Ojek2 so that 2 users who logged in can chat in realtime using Firebase Cloud Messaging and MEAN stack.

## Members

* 13515053 - Yohanes Jhouma Parulian
* 13515125 - M. Rafid A.
* 13515134 - Dicky Novanto

## Deskripsi singkat sistem

> MEAN (MonggoDB, ExpressJS, AngularJS, Node.js) merupakan sekumpulan framework JavaScript yang digunakan untuk mendevelop web. Mulai dari   client-side sampai server-side, MEAN merupakan full-stack development tool-kit. Pada tugas ini MEAN Stack digunakan untuk bagian front-end dan back-end dari chat app.

> FCM (Firebase Cloud Messaging) merupakan suatu service dari google yang dapat digunakan untuk pengiriman pesan lintas platform. FCM pada tugas ini digunakan untuk mengirim dan menerima pesan dengan menggunakan token.

![Gambar Arsitektur Umum](./img/arsitektur_umum.png)


## Prerequisite

[IntelliJ IDEA](https://www.jetbrains.com/help/idea/installing-and-launching.html)

[Jetty](https://plugins.jetbrains.com/plugin/7505-idea-jetty-runner)

[Java SE Development Kit 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)

[MySQL Connector/J](https://dev.mysql.com/downloads/connector/j/5.1.html)

[NPM](https://www.npmjs.com/#product-navigation)

[FCM](https://firebase.google.com)



## Skenario Chatting
Skenario penggunaan aplikasi adalah sebagai berikut.
Misal pengguna A adalah non driver, dan pengguna B adalah driver.
1. A dan B login untuk masuk ke aplikasi.
2. B melakukan finding order pada halaman Order. A memasuki halaman Order.
3. A melakukan order dan memilih driver yang sedang online dan tersedia (driver B).
4. Kotak chat akan muncul di halaman Chat Driver pada layar A. Kotak chat juga akan muncul pada halaman Order pada B.
5. A mengetikkan pesan, dan menekan tombol kirim.
6. Pesan dikirim ke B melalui chat server dan FCM.
7. Ketika pesan sudah diterima di B, kotak chat pada layar B akan muncul.
8. B dapat membalas chat dari A.
9. Apabila A sudah melakukan submit rating maka chatbox pada B akan hilang dan kembali menampilkan halaman finding order.

## Skenario Umum Program
Skenario program selain chat, pada umumnya sama seperti tugas 2. Akan tetapi, metode pengecekan token pada identity service sedikit berbeda.

Identity Service harus mengecek:
1. Apakah access token ini sudah kadaluarsa?
2. Apakah access token ini digunakan pada browser yang berbeda?
3. Apakah access token ini digunakan dengan koneksi internet yang berbeda?

Jika jawaban salah satu pertanyaan tersebut adalah "ya", maka identity service akan memberikan respon error dan detail errornya.



## Pembagian Tugas

Chat App Front-end :
1. Halaman Chat : 13515125  
2. Halaman Finding Order : 13515134


Chat REST Service:  
1. Database Connection : 13515053  
2. Pengiriman Token : 13515134

Fitur security (IP, User-agent) :
1. New Token : 13515053
2. Identity Service Check : 13515125


## Special thanks to

Dosen IF3110
Yudistira Dwi Wardhana | Riza Satria Perdana | Muhammad Zuhri Catur Candra

Asisten IF3110
Ade | Johan | Kristianto | Micky | Michael | Rangga | Raudi | Robert | Sashi
