
import requests
from bs4 import BeautifulSoup
import psycopg2

# Hürriyet Bigpara'dan verileri çekme
URL = "https://bigpara.hurriyet.com.tr/borsa/canli-borsa/"
page = requests.get(URL)

# Parsing the data with BeautifulSoup
soup = BeautifulSoup(page.content, "html.parser")

# PostgreSQL Docker konteynerine bağlan
conn = psycopg2.connect(
    dbname="spmapi_db",
    user="postgres",
    password="Chandler1.",
    host="127.0.0.1",  # veya "127.0.0.1"
    port="5432"
)

cur = conn.cursor()

# Verilerin çekilmesi ve işlenmesi
full_data = soup.find(class_="tBody ui-unsortable", id="sortable")

if full_data:
    # <li> 
    share_abbreviation_name = full_data.find_all("li", class_="cell064 tal arrow")
    share_last = full_data.find_all("li", class_="cell048 node-c")
    share_buy = full_data.find_all("li", class_="cell048 node-f")
    share_sell = full_data.find_all("li", class_="cell048 node-g")

    if share_abbreviation_name and share_last and share_buy and share_sell:
        for i in range(min(len(share_abbreviation_name), len(share_last), len(share_buy), len(share_sell))):
            abbreviation_tag = share_abbreviation_name[i].find_all("a")
            if abbreviation_tag and len(abbreviation_tag) > 1:
                abbreviation_name = abbreviation_tag[-1].text.strip()  
            else:
                abbreviation_name = "N/A"  # Hata durumunda

            last_price = share_last[i].text.strip()  # Son fiyat
            buy_price = share_buy[i].text.strip()    # Alış fiyatı
            sell_price = share_sell[i].text.strip()  # Satış fiyatı

            # Verileri PostgreSQL veritabanına kaydet
            cur.execute("""
                INSERT INTO stocks (symbol, name, price, created_at)
                VALUES (%s, %s, %s, now())
                ON CONFLICT (symbol) DO UPDATE
                SET price = EXCLUDED.price, updated_at = now();
            """, (abbreviation_name, abbreviation_name, last_price))

        conn.commit()  # Veritabanına kaydet
    else:
        print("Gerekli veriler bulunamadı.")
else:
    print("Veri tablosu bulunamadı.")

# Bağlantıyı kapat
cur.close()
conn.close()
