# Java---Multithreading
Yazılım Laboratuvarı 1-2. ödev.

## Özet
Bu projede sunucu istek yoğunluğunu istendiği
şekilde multithread yöntemi ile kontrol edip işin alt
sunucularla paylaşılmasını sağlıyoruz. Kaç alt
sunucu olacağını oluşturduğumuz kontrol sunucusu
sağlıyor. Gerektiği yerde Yeni sunucu oluşturuyor
veya gereksiz sunucuyu durdurup siliyor. Kontrol
sunucusu yoğunluğunu kontrol ettiği herbir
sunucuyu işlem süresince durdurur ki kontrol
edilirken sunucunun doluluk oranı değişmesin.

## Geliştirme Ortamı
Sunucu istek kontrolü projesi Java dilinde
yazılmıştır.Geliştirme ortamı olarak “NetBeans
IDE 8.2” kullanılmıştır.Grafik kütüphanesi olarak
Swing kullanılmıştır.

## Özetle Algoritma
Programın başlatılmasıyla iki alt sunucu ve
kontrol sunucusunu başlatılır. Sonra main'de
count(int) 10000'i geçmediği sürece her 20 ms'de
bir değer azaltılıp 50 ms'de bir değer artırılır. Eğer
count 10000'i geçerse varolan herbir sunucu
durdurulur ve program son bulur. Kontrol sunucu'su
sürekli alt sunucuların doluluk oranını kontrol edip
gerekli noktalarda sunucuları durdurup siler, yeni
sunucu oluşturur veya sunucuların doluluk
oranlarına göre önceliklerini günceller. Alt
sunucular 30 ms'de bir değer azaltıp 50 ms'de bir
artırır. Programın başlamasıyla gui ekranı da açılır
ve sunucuların doluluk oranını gösterir.


## KAYNAKÇA
#### Thread genel
https://docs.oracle.com/javase/tutorial/essential/concurrency/runthread.html
http://tutorials.jenkov.com/java-concurrency/creating-and-starting-threads.html
https://www.tutorialspoint.com/java/java_multithreading.htm
https://www.geeksforgeeks.org/java-lang-thread-classjava/https://www.journaldev.com/1079/multithreading-in-java
#### Senkronizasyon için:
https://www.tutorialspoint.com/java/java_thread_synchronization.htm
https://javarevisited.blogspot.com/2011/04/synchronization-in-java-synchronized.html
https://www.geeksforgeeks.org/synchronized-in-java/
