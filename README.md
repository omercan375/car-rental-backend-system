# 🚗 Araç Kiralama Backend Sistemi

Bu proje, **Spring Boot kullanılarak geliştirilmiş backend odaklı bir araç kiralama sistemidir**.  
Gerçek bir sistemde bulunan **kiralama, ödeme, admin onayı ve ceza mekanizması** gibi süreçleri simüle eder.

> ⚠️ Bu proje backend ağırlıklıdır. Frontend sadece API'leri test etmek ve görselleştirmek amacıyla kullanılmıştır.

---

# 📌 Özellikler

- 🔐 JWT tabanlı kimlik doğrulama
- 🚗 Araç kiralama akışı yönetimi
- 💳 Cüzdan (wallet) tabanlı ödeme sistemi
- 🛠️ Admin onay mekanizması
- ⏱️ Otomatik çalışan scheduled işlemler
- 📊 Kiralama geçmişi görüntüleme
- 🔄 Status (durum) bazlı sistem akışı

---

# 🧠 Sistem Akışı

1. Kullanıcı araç seçer ve kiralar
2. Sistem aracı **rezerve eder** (çift kiralamayı engeller)
3. Ödeme kullanıcı cüzdanından düşülür
4. Kiralama durumu **ACTIVE** olur
5. Süre dolunca sistem otomatik olarak → **WAITING_APPROVE**
6. Admin:
   - Kiralamayı **COMPLETED** yapar
   - veya **PENALTY** uygular
7. Araç tekrar **AVAILABLE** olur

---

# 🔄 Kiralama Durum Akışı
