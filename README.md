# 💄 Beauty Club Mobile App

Aplikasi membership beauty clinic berbasis Android yang dikembangkan menggunakan **Kotlin**, **Jetpack Compose**, **MVVM Architecture**, dan **Room Database**.

Aplikasi ini memungkinkan pengguna untuk melakukan registrasi dan login, mengelola data member, mencatat transaksi treatment, mengumpulkan poin loyalitas, menukarkan reward, serta melihat kartu member digital secara realtime menggunakan database lokal.

---

## 🛠️ Teknologi yang Digunakan

* Kotlin
* Jetpack Compose
* MVVM Architecture
* Room Database
* StateFlow
* Navigation Compose
* Material 3

## 📱 Fitur Utama

### 🔐 Authentication

* Registrasi member baru
* Login member
* Logout akun

### 🏠 Home

* Menampilkan informasi member
* Menampilkan total poin yang dimiliki
* Menampilkan riwayat aktivitas terbaru
* Navigasi ke fitur My Card, Reward, Transaction, dan Profile

### 💳 My Card

* Digital membership card
* QR Code member
* Nomor kartu member otomatis
* Informasi tier member (Silver, Gold, Platinum)
* Menampilkan total poin terkini

### 🎁 Reward

* Menampilkan daftar reward yang tersedia
* Redeem reward menggunakan poin
* Pengurangan poin otomatis
* Riwayat redeem tersimpan pada database

### 💰 Transaction

* Menambahkan transaksi treatment
* Perhitungan poin otomatis berdasarkan nominal transaksi
* Menampilkan riwayat transaksi
* Filter transaksi berdasarkan kategori

### 👤 Profile

* Menampilkan data member
* Edit profil member
* Menyimpan perubahan ke database
* Logout dan kembali ke halaman splash

---

## 📂 Struktur Folder

```text
app/src/main/java/com/example/beautyclub

├── data
│   ├── local
│   │   ├── dao
│   │   ├── entity
│   │   └── BeautyClubDatabase.kt
│   ├── repository
│   └── reward
│
├── navigation
│
├── ui
│   ├── auth
│   ├── home
│   ├── profile
│   ├── reward
│   └── transaction
│
├── viewmodel
│
└── theme
```

---

## 📖 Penjelasan Folder

| Folder          | Deskripsi                                          |
| --------------- | -------------------------------------------------- |
| data/local      | Berisi Room Database, DAO, dan Entity              |
| data/repository | Penghubung antara ViewModel dan Database           |
| data/reward     | Data dummy reward aplikasi                         |
| navigation      | Pengaturan navigasi antar halaman                  |
| ui              | Seluruh tampilan aplikasi berbasis Jetpack Compose |
| viewmodel       | Logic aplikasi dan state management                |
| theme           | Warna, typography, dan styling aplikasi            |

---

## 🗄️ Database Schema

### Members

| Field    | Tipe Data |
| -------- | --------- |
| id       | Int       |
| name     | String    |
| email    | String    |
| phone    | String    |
| password | String    |
| points   | Int       |

### Transactions

| Field         | Tipe Data |
| ------------- | --------- |
| id            | Int       |
| memberId      | Int       |
| treatmentName | String    |
| amount        | Double    |
| pointEarned   | Int       |
| date          | String    |

---

## 📸 Screenshot

Tambahkan screenshot aplikasi pada bagian berikut:

### Login

![Login](screenshots/login.png)

### Home

![Home](screenshots/home.png)

### My Card

![My Card](screenshots/mycard.png)

### Transaction

![Transaction](screenshots/transaction.png)

### Reward

![Reward](screenshots/reward.png)

### Profile

![Profile](screenshots/profile.png)

---

## 🚀 Cara Menjalankan Project

1. Clone repository

```bash
git clone https://github.com/username/beautyclub.git
```

2. Buka project menggunakan Android Studio

3. Lakukan Gradle Sync

4. Jalankan aplikasi pada Emulator atau Android Device

---

## 🎯 Arsitektur

Project ini menggunakan pola **MVVM (Model-View-ViewModel)**:

```text
UI (Compose)
      │
      ▼
ViewModel
      │
      ▼
Repository
      │
      ▼
Room Database
```

Arsitektur ini membantu memisahkan tampilan, business logic, dan data sehingga kode lebih mudah dikelola dan dikembangkan.

---

## 👩‍💻 Pengembang

**Alma Khusnia**

Project Mata Kuliah Mobile Programming

Universitas Pembangunan Nasional "Veteran" Jawa Timur
