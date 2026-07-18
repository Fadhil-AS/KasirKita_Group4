# KasirKita — Spring Boot + MySQL

Aplikasi kasir (Point of Sale) berbasis REST API. Migrasi dari aplikasi console
Java murni menjadi arsitektur berlapis: **Entity → Repository → Service → Controller**.

## Prasyarat

- JDK 21
- Maven 3.9+
- MySQL 8.x berjalan di `localhost:3306`

## Konfigurasi

Kredensial DB dibaca dari environment variable (default untuk dev lokal ada di
`application.properties`). Database `kasirkita` dibuat otomatis
(`createDatabaseIfNotExist=true`).

```bash
export DB_USER=root
export DB_PASS=rahasia
export ADMIN_PASS=admin123 
```

## Menjalankan

```bash
mvn spring-boot:run
```

Aplikasi berjalan di `http://localhost:8080`. Saat pertama kali start, user
`admin` dibuat otomatis (role `ADMIN`, password dari `ADMIN_PASS`, default `admin123`).

## Test (tanpa MySQL)

```bash
mvn test
```

Test memakai H2 in-memory, tidak butuh MySQL.

## Ringkasan Endpoint

| Method | Endpoint | Auth |
|---|---|---|
| GET/POST | `/api/barang` | publik |
| GET/PUT/DELETE | `/api/barang/{id}` | publik (GET publik) |
| POST | `/api/pembeli` | publik |
| GET | `/api/pembeli`, `/api/pembeli/{id}` | **login** (BR4) |
| GET/POST | `/api/supplier` | publik |
| POST | `/api/transaksi` | publik |
| GET | `/api/transaksi/{id}` | **login** (BR4) |
| POST | `/api/pembayaran` | publik |
| POST | `/api/auth/register` | publik |
| POST | `/api/auth/login` | publik |

Endpoint ber-login memakai **HTTP Basic** (username/password user). Password
di-hash BCrypt.

## Contoh Pemakaian (curl)

```bash
# 1. Supplier
curl -X POST localhost:8080/api/supplier -H 'Content-Type: application/json' \
  -d '{"namaSupp":"PT Indofood","noTelp":"08123456789","alamat":"Jakarta"}'

# 2. Barang (supplierId dari langkah 1)
curl -X POST localhost:8080/api/barang -H 'Content-Type: application/json' \
  -d '{"namaBarang":"Indomie","price":10000.00,"stok":100,"supplierId":1}'

# 3. Pembeli
curl -X POST localhost:8080/api/pembeli -H 'Content-Type: application/json' \
  -d '{"namaPembeli":"Risky","jenisKelamin":"L","noTelepon":"08987654321","alamat":"Bandung"}'

# 4. Transaksi (stok otomatis berkurang, total dihitung Service)
curl -X POST localhost:8080/api/transaksi -H 'Content-Type: application/json' \
  -d '{"pembeliId":1,"items":[{"barangId":1,"quantity":3}]}'

# 5. Pembayaran (kembalian dihitung; ditolak jika uang < total)
curl -X POST localhost:8080/api/pembayaran -H 'Content-Type: application/json' \
  -d '{"transaksiId":1,"uangDibayar":50000}'

# 6. Lihat detail transaksi -> butuh login (BR4)
curl -u admin:admin123 localhost:8080/api/transaksi/1

# 7. Register user baru
curl -X POST localhost:8080/api/auth/register -H 'Content-Type: application/json' \
  -d '{"username":"kasir1","password":"rahasia123","role":"KASIR"}'
```

## Perbaikan Bug dari Versi Lama (PRD bagian 2.2)

| Bug | Perbaikan |
|---|---|
| M2 — warisan domain keliru | Tiap kelas jadi entity mandiri, tanpa `extends transaksi` |
| M3 — uang `double` | `BigDecimal` + kolom `DECIMAL(15,2)` |
| M4 — telepon `int` | `VARCHAR(20)` |
| M5 — login tidak tersimpan | Spring Security + BCrypt, user dari tabel `user` |
| M6 — logika stok terbalik | Stok dikurangi di `TransaksiService`, guard tidak boleh negatif |
| M7 — rumus kembalian beda | Satu rumus di `PembayaranService`: `uang_dibayar - total_harga` |
| M8 — tanpa layer | Entity / Repository / Service / Controller terpisah |
