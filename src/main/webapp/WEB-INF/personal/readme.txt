
URL pro javascript pro zobrazeni reklamy je nasledujici:
/rest/tip/{SEKCE}/{STYL}/{POCET}
kde
{SEKCE} je url_name zavedene v databaze pro sekci
{STYLE} je orientace widgetu. Mozne hodnoty: VERTICAL, HORIZONTAL
{POCET} je pocet reklam vracenych najednou


URL adresa pro presmerovani na URL adresu inzeratu je:

/advert?id={ADVERT_ID}
kde
{ADVERT_ID} je identifikator reklamy ze systemu (primary key)

