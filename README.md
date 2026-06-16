
# 💄 Beauty Club Mobile App

## Kelompok

| Nama           | NRP      |
| -------------- | -------- |
| Alma Khusnia | 5025231063 |
| Rosidah Darman | 5025231307 |


---

## Deskripsi Aplikasi

Beauty Club merupakan aplikasi membership beauty clinic yang membantu pelanggan mengelola keanggotaan secara digital. Pengguna dapat melihat informasi member, mengumpulkan poin dari setiap transaksi treatment, menukarkan reward, mengakses kartu member digital, serta memantau riwayat aktivitas keanggotaan dalam satu aplikasi yang praktis dan mudah digunakan.

---

## Fitur Utama

### 🚀 Splash Screen

Menampilkan halaman pembuka aplikasi sebelum pengguna diarahkan ke halaman login.

### 🔐 Authentication

- Login: Memungkinkan member masuk ke aplikasi menggunakan email dan password yang telah terdaftar
- Register: Memungkinkan pengguna membuat akun member baru dengan mengisi data diri yang diperlukan.

### 🏠 Home

- Menampilkan informasi utama member seperti total poin, ringkasan aktivitas, dan akses cepat ke fitur **My Card, Rewards, dan Transaction**.
- My Card: Menampilkan kartu member digital yang berisi informasi member, QR Code, nomor kartu, dan status membership.
- Rewards: Menampilkan daftar reward yang dapat ditukarkan menggunakan poin member.
- Reward Success: Menampilkan konfirmasi bahwa proses penukaran reward berhasil dilakukan dan poin telah diperbarui.

### 💰 Transaction

- Menampilkan riwayat transaksi treatment yang pernah dilakukan oleh member.
- Add Transaction: Memungkinkan penambahan transaksi treatment baru yang secara otomatis menambahkan poin ke akun member.
- Transaction Success: Menampilkan informasi bahwa transaksi berhasil dilakukan beserta jumlah poin yang diperoleh.

### 👤 Profile

- Menampilkan informasi profil member serta menyediakan fitur untuk edit data profil.
- LogoutMemungkinkan pengguna keluar dari akun dan kembali ke halaman awal aplikasi.
---
## 🛠️ Teknologi yang Digunakan

* Kotlin
* Jetpack Compose
* MVVM Architecture
* Room Database
* Navigation Compose
* Material 3
---

## 📂 Struktur Folder

```text
app/src/main/java/com/example/beautyclub

├── data
│   ├── local
│   │   ├── dao
│   │   │     ├── MemberDao.kt
│   │   │     └── TransactionDao.kt
│   │   ├── entity
│   │   │     ├── MemberEntity.kt
│   │   │     └── TransactionEntity.kt
│   │   └── BeautyClubDatabase.kt
│   ├── repository
│   │   ├── MemberRepository.kt
│   │   └── TransactionRepository.kt
│   └── reward
│
├── navigation
│   ├── NavGraph.kt
│   └── Screen.kt
│
├── ui
│   ├── auth
│   │    ├── LoginScreen.kt
│   │    ├── RegisterScreen.kt
│   │    └── SplashScreen.kt
│   ├── component
│   │    ├── BeautyCard.kt
│   │    ├── PrimaryButton.kt
│   │    ├── TextFieldColors.kt
│   │    └── TransactionCard.kt
│   ├── home
│   │    ├── HomeScreen.kt
│   │    ├── MyCardScreen.kt
│   │    ├── RewardScreen.kt
│   │    └── RewardSuccessScreen.kt
│   ├── profile
│   │    └── ProfileScreen.kt
│   ├── theme
│   │    ├── Color.kt
│   │    ├── Theme.kt
│   │    └── Type.kt
│   └── transaction
│        ├── AddTransactionScreen.kt
│        ├── TransactionScreen.kt
│        └── TransactionSuccessScreen.kt
│
├── viewmodel
│
└── MainActivity.kt
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

<img width="1281" height="245" alt="image" src="https://github.com/user-attachments/assets/a02712c4-5642-4ae7-9a08-2b1a8e7e3124" />


### Transactions

| Field         | Tipe Data |
| ------------- | --------- |
| id            | Int       |
| memberId      | Int       |
| treatmentName | String    |
| amount        | Double    |
| pointEarned   | Int       |
| date          | String    |

<img width="1283" height="688" alt="image" src="https://github.com/user-attachments/assets/6343a2f9-180e-4e38-b030-d532be822e29" />

---

## 📸 Screenshot Aplikasi

### Splash Screen

<img width="242" height="546" alt="image" src="https://github.com/user-attachments/assets/bfa90aa2-1dec-48df-9d35-52cffddd9026" />


### Registrasi

<img width="246" height="542" alt="image" src="https://github.com/user-attachments/assets/1ddf8a04-2ab9-4446-8f47-5b3b676c39eb" />

### Login

<img width="243" height="546" alt="image" src="https://github.com/user-attachments/assets/d8db4d12-3c97-488c-bf1b-adbd36d0f6f8" />

### Home

<img width="243" height="545" alt="image" src="https://github.com/user-attachments/assets/bacd2b42-072b-4843-a22e-e1d325f90ba8" />

### My Card

<img width="246" height="545" alt="image" src="https://github.com/user-attachments/assets/b811dc21-4c15-4222-ba58-fc24282357bc" />

### Reward

<img width="242" height="546" alt="image" src="https://github.com/user-attachments/assets/ae41a1ad-620e-4770-8d53-9c6a70d55e31" />


### Transaction

<img width="245" height="547" alt="image" src="https://github.com/user-attachments/assets/da81fc26-ad9c-4916-987c-359fba3b42cc" />


### Profile

<img width="245" height="546" alt="image" src="https://github.com/user-attachments/assets/59c690f6-6d5b-43e5-9cfe-8e7b8834ae95" />


---

## 🚀 Cara Menjalankan Project

1. Clone repository

```bash
git clone https://github.com/username/beautyclub.git
```

2. Buka project menggunakan Android Studio

3. Lakukan Gradle Sync

4. Jalankan aplikasi pada Emulator atau Android Device
