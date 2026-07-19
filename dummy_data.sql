-- Dummy data KasirKita: 100 supplier, 100 barang (produk Indonesia nyata),
-- 100 pembeli (nama orang Indonesia), 100 transaksi + 200 detail.
-- Jalankan pada tabel kosong (id auto-increment mulai 1). MySQL 8.x.
-- Reset dulu (auto-increment ikut ter-reset):
--   SET FOREIGN_KEY_CHECKS=0;
--   TRUNCATE pembayaran; TRUNCATE detail_transaksi; TRUNCATE transaksi;
--   TRUNCATE barang; TRUNCATE pembeli; TRUNCATE supplier;
--   SET FOREIGN_KEY_CHECKS=1;

USE kasirkita;

-- ===== 100 supplier (gaya distributor) =====
INSERT INTO supplier (nama_supp, no_telp, alamat, created_at, updated_at)
WITH RECURSIVE seq(n) AS (SELECT 1 UNION ALL SELECT n+1 FROM seq WHERE n < 100)
SELECT CONCAT(
         ELT(1 + (n % 3), 'PT', 'CV', 'UD'), ' ',
         ELT(1 + (n % 15), 'Sumber','Cahaya','Berkah','Mitra','Sinar','Karya','Surya','Anugerah','Sentosa','Makmur','Barokah','Jaya','Cipta','Tunas','Dwi'), ' ',
         ELT(1 + ((n DIV 3) % 12), 'Niaga','Pangan','Sembako','Rejeki','Abadi','Makmur','Sentosa','Utama','Mandiri','Lestari','Sejahtera','Bersama')
       ),
       CONCAT('021-', LPAD(20000000 + n * 137, 8, '0')),
       CONCAT('Jl. ', ELT(1 + (n % 10), 'Raya','Merdeka','Gatot Subroto','Ahmad Yani','Sudirman','Diponegoro','Gajah Mada','Pahlawan','Veteran','Cendrawasih'),
              ' No. ', 1 + (n % 200), ', ',
              ELT(1 + (n % 10), 'Jakarta','Bandung','Surabaya','Semarang','Medan','Makassar','Yogyakarta','Denpasar','Palembang','Bekasi')),
       NOW(), NOW()
FROM seq;

-- ===== 100 barang: produk retail Indonesia nyata, harga & stok bervariasi =====
INSERT INTO barang (nama_barang, price, stok, supplier_id, created_at, updated_at)
SELECT nama, price, stok,
       ((ROW_NUMBER() OVER () - 1) % 100) + 1,
       NOW(), NOW()
FROM (
  SELECT 'Indomie Goreng' AS nama, 3000 AS price, 235 AS stok
  UNION ALL SELECT 'Indomie Kari Ayam',3000,188
  UNION ALL SELECT 'Indomie Soto Mie',3000,210
  UNION ALL SELECT 'Mie Sedaap Goreng',2800,176
  UNION ALL SELECT 'Mie Sedaap Kari Spesial',2800,142
  UNION ALL SELECT 'Pop Mie Ayam Bawang',6500,95
  UNION ALL SELECT 'Sarimi Isi 2 Ayam',3500,130
  UNION ALL SELECT 'Supermi Ayam Bawang',3000,118
  UNION ALL SELECT 'Beras Rojolele 5kg',68000,42
  UNION ALL SELECT 'Beras Pandan Wangi 5kg',72000,38
  UNION ALL SELECT 'Beras Setra Ramos 10kg',130000,25
  UNION ALL SELECT 'Minyak Goreng Bimoli 1L',19000,88
  UNION ALL SELECT 'Minyak Goreng Sania 2L',36000,54
  UNION ALL SELECT 'Minyak Goreng Filma 1L',18500,70
  UNION ALL SELECT 'Gula Pasir Gulaku 1kg',16000,96
  UNION ALL SELECT 'Gula Merah Batok 500g',12000,64
  UNION ALL SELECT 'Tepung Terigu Segitiga Biru 1kg',13000,78
  UNION ALL SELECT 'Tepung Beras Rose Brand 500g',8000,90
  UNION ALL SELECT 'Garam Dolphin 250g',4000,150
  UNION ALL SELECT 'Kecap Manis Bango 220ml',14000,82
  UNION ALL SELECT 'Kecap ABC 275ml',15000,76
  UNION ALL SELECT 'Saus Sambal ABC 335ml',16000,68
  UNION ALL SELECT 'Saus Tomat Del Monte 200ml',12000,60
  UNION ALL SELECT 'Sambal Indofood Pedas 105ml',9000,74
  UNION ALL SELECT 'Teh Celup Sariwangi 25s',8500,110
  UNION ALL SELECT 'Teh Botol Sosro 450ml',5000,145
  UNION ALL SELECT 'Teh Pucuk Harum 350ml',3500,168
  UNION ALL SELECT 'Kopi Kapal Api Special 165g',12000,102
  UNION ALL SELECT 'Kopi ABC Susu 10s',11000,96
  UNION ALL SELECT 'Good Day Cappuccino 10s',13000,84
  UNION ALL SELECT 'Nescafe Classic 100g',28000,46
  UNION ALL SELECT 'Milo Sachet 22g',2500,190
  UNION ALL SELECT 'Susu Ultra Milk Coklat 250ml',6500,128
  UNION ALL SELECT 'Frisian Flag Kental Manis 370g',12000,88
  UNION ALL SELECT 'SGM Eksplor 1+ 400g',52000,30
  UNION ALL SELECT 'Dancow Fortigro 400g',48000,34
  UNION ALL SELECT 'Aqua 600ml',3500,200
  UNION ALL SELECT 'Aqua Galon 19L',20000,40
  UNION ALL SELECT 'Le Minerale 600ml',3000,175
  UNION ALL SELECT 'Coca-Cola 390ml',5500,132
  UNION ALL SELECT 'Sprite 390ml',5500,120
  UNION ALL SELECT 'Fanta Strawberry 390ml',5500,115
  UNION ALL SELECT 'Fruit Tea Apple 350ml',4000,108
  UNION ALL SELECT 'Pocari Sweat 500ml',8000,92
  UNION ALL SELECT 'Mizone Lychee Lemon 500ml',5000,100
  UNION ALL SELECT 'Chitato Sapi Panggang 68g',10000,86
  UNION ALL SELECT 'Lays Rumput Laut 68g',10500,80
  UNION ALL SELECT 'Taro Net Seaweed 70g',9000,78
  UNION ALL SELECT 'Qtela Singkong 60g',8500,72
  UNION ALL SELECT 'Chiki Balls Keju 55g',6000,94
  UNION ALL SELECT 'Oreo Original 133g',9500,88
  UNION ALL SELECT 'Biskuat Coklat 112g',6000,96
  UNION ALL SELECT 'Roma Kelapa 300g',11000,70
  UNION ALL SELECT 'Better Malkist 108g',7500,82
  UNION ALL SELECT 'Khong Guan Kaleng 650g',55000,28
  UNION ALL SELECT 'SilverQueen Chunky Bar 65g',15000,66
  UNION ALL SELECT 'SilverQueen Almond 62g',15000,62
  UNION ALL SELECT 'Cadbury Dairy Milk 65g',16000,58
  UNION ALL SELECT 'Beng-Beng 20g',2000,210
  UNION ALL SELECT 'Chocolatos Wafer 32g',3000,180
  UNION ALL SELECT 'Tango Wafer Coklat 130g',9000,74
  UNION ALL SELECT 'Nabati Richeese 50g',3500,165
  UNION ALL SELECT 'Kacang Garuda 200g',12000,68
  UNION ALL SELECT 'Kacang Dua Kelinci 200g',11500,64
  UNION ALL SELECT 'Sabun Lifebuoy Merah 85g',4000,140
  UNION ALL SELECT 'Sabun Lux Soft Touch 85g',4200,128
  UNION ALL SELECT 'Shampo Sunsilk Hitam 160ml',22000,56
  UNION ALL SELECT 'Shampo Clear Men 160ml',24000,52
  UNION ALL SELECT 'Pepsodent White 190g',15000,90
  UNION ALL SELECT 'Close Up Menthol 160g',14000,84
  UNION ALL SELECT 'Sikat Gigi Formula Silky',8000,96
  UNION ALL SELECT 'Rinso Anti Noda 770g',23000,60
  UNION ALL SELECT 'Daia Deterjen Putih 850g',18000,66
  UNION ALL SELECT 'Molto Pewangi 800ml',20000,54
  UNION ALL SELECT 'Sunlight Jeruk Nipis 755ml',16000,88
  UNION ALL SELECT 'Mama Lemon 780ml',15000,82
  UNION ALL SELECT 'Baygon Spray 600ml',32000,44
  UNION ALL SELECT 'HIT Aerosol 675ml',30000,46
  UNION ALL SELECT 'Tissue Paseo 250s',18000,70
  UNION ALL SELECT 'Tissue Nice 200s',14000,76
  UNION ALL SELECT 'Pampers Popok M 24s',58000,32
  UNION ALL SELECT 'MamyPoko Pants L 26s',62000,28
  UNION ALL SELECT 'Softex Daun Sirih 20s',12000,80
  UNION ALL SELECT 'Charm Extra Comfort 20s',13000,78
  UNION ALL SELECT 'Sampoerna A Mild 16',30000,120
  UNION ALL SELECT 'Gudang Garam Surya 12',24000,110
  UNION ALL SELECT 'Djarum Super 12',23000,105
  UNION ALL SELECT 'Korek Api Gas Tokai',3000,160
  UNION ALL SELECT 'Baterai ABC AA 4s',12000,90
  UNION ALL SELECT 'Baterai Energizer AA 2s',18000,60
  UNION ALL SELECT 'Telur Ayam Negeri 1kg',28000,58
  UNION ALL SELECT 'Roti Tawar Sari Roti',13000,72
  UNION ALL SELECT 'Roti Sobek Coklat',15000,64
  UNION ALL SELECT 'Selai Srikaya Morin 170g',18000,48
  UNION ALL SELECT 'Mentega Blue Band 200g',12000,74
  UNION ALL SELECT 'Keju Kraft Cheddar 170g',22000,50
  UNION ALL SELECT 'Sosis So Nice 6s',15000,68
  UNION ALL SELECT 'Nugget Fiesta 500g',42000,36
  UNION ALL SELECT 'Bakso Kanzler 360g',38000,34
  UNION ALL SELECT 'Kornet Pronas 340g',26000,42
) p;

-- ===== 100 pembeli: nama orang Indonesia (depan + belakang), gender konsisten =====
INSERT INTO pembeli (nama_pembeli, jenis_kelamin, no_telepon, alamat, created_at, updated_at)
WITH RECURSIVE seq(n) AS (SELECT 1 UNION ALL SELECT n+1 FROM seq WHERE n < 100)
SELECT CONCAT(
         IF(n % 2 = 0,
            ELT(1 + ((n DIV 2) % 15), 'Siti','Dewi','Ratna','Sri','Ayu','Indah','Putri','Wulan','Nurul','Rina','Fitri','Maya','Yuni','Anisa','Lestari'),
            ELT(1 + ((n DIV 2) % 15), 'Budi','Andi','Agus','Dedi','Eko','Rizki','Bagus','Hendra','Joko','Wahyu','Fajar','Dimas','Arif','Bayu','Yusuf')),
         ' ',
         ELT(1 + ((n * 3) % 20), 'Santoso','Wijaya','Kusuma','Pratama','Nugroho','Saputra','Hidayat','Wibowo','Setiawan','Halim','Gunawan','Permana','Maulana','Ramadhan','Firmansyah','Suryani','Handayani','Purnama','Rahmawati','Susanti')
       ),
       IF(n % 2 = 0, 'P', 'L'),
       CONCAT('0813', LPAD(n * 91, 8, '0')),
       CONCAT('Jl. ', ELT(1 + (n % 10), 'Melati','Kenanga','Anggrek','Mawar','Cempaka','Flamboyan','Teratai','Dahlia','Kamboja','Bougenville'),
              ' No. ', 1 + (n % 150), ', ',
              ELT(1 + ((n DIV 2) % 10), 'Jakarta','Bandung','Surabaya','Semarang','Medan','Makassar','Yogyakarta','Denpasar','Bekasi','Depok')),
       NOW(), NOW()
FROM seq;

-- ===== 100 transaksi (total_harga diisi belakangan; tiap ke-7 walk-in) =====
INSERT INTO transaksi (pembeli_id, tanggal, total_harga, created_at, updated_at)
WITH RECURSIVE seq(n) AS (SELECT 1 UNION ALL SELECT n+1 FROM seq WHERE n < 100)
SELECT IF(n % 7 = 0, NULL, ((n - 1) % 100) + 1),
       NOW() - INTERVAL n HOUR,
       0,
       NOW(), NOW()
FROM seq;

-- ===== 200 detail_transaksi (2 item per transaksi) =====
INSERT INTO detail_transaksi (transaksi_id, barang_id, quantity, subtotal, created_at, updated_at)
WITH RECURSIVE seq(n) AS (SELECT 1 UNION ALL SELECT n+1 FROM seq WHERE n < 200)
SELECT t.id, b.id, q, b.price * q, NOW(), NOW()
FROM (
    SELECT FLOOR((n - 1) / 2) + 1 AS transaksi_id,
           ((n * 7) % 100) + 1     AS barang_id,
           ((n * 3) % 5) + 1        AS q
    FROM seq
) d
JOIN transaksi t ON t.id = d.transaksi_id
JOIN barang b    ON b.id = d.barang_id;

-- ===== total_harga = SUM(subtotal) per transaksi =====
UPDATE transaksi t
JOIN (SELECT transaksi_id, SUM(subtotal) tot FROM detail_transaksi GROUP BY transaksi_id) s
  ON s.transaksi_id = t.id
SET t.total_harga = s.tot;

-- ===== kurangi stok barang sesuai qty terjual (tak boleh negatif) =====
UPDATE barang b
JOIN (SELECT barang_id, SUM(quantity) q FROM detail_transaksi GROUP BY barang_id) d
  ON d.barang_id = b.id
SET b.stok = GREATEST(b.stok - d.q, 0);
