from random import randint, choice

# ------------------------------------
# Dostajemy imię bohatera
imie = input('Podaj imię twojego bohatera: ')  # Wprowadzamy imię bohatera
zycie = 100  # Ilość zdrowia
zbroja = 20  # Początkowa zbroja
pieniadze = 100  # Ilość pieniędzy
poziom = 1  # Poziom bohatera
doswiadczenie = 0  # Doświadczenie bohatera
ekwipunek = {"Sok": 2, "Herbata": 2}  # Ekwiwipunek bohatera
lokacja = "Start"  # Początkowa lokalizacja

# -----------------------------------
# Lista fraz przeciwników przed śmiercią
frazy_smierci = { "Niee, jak mogłeś..",
                 "To jeszcze nie koniec... Wrócę silniejszy...",
                 "Nie spodziewałem się tego...",
                 "Żegnaj okrutny świecie...",
                 "Moja zemsta będzie straszna!..",
                 "To boli..."}

#------------------------------------
# Zwykły atak bohatera
def zwykly_atak():
    return randint(3, 10) + poziom  # Losowe obrażenia + poziom

#----------------------------------------
# Atak pomidorem
def atak_pomidorem():
    global pieniadze
    if pieniadze < 10:  # Sprawdzanie ilości pieniędzy
        print("Nie masz wystarczającej ilości pieniędzy!")
        return 0
    pieniadze -= 10  # Odejmowanie pieniędzy
    return randint(13, 20) + poziom  # Obrażenia od ataku pomidorem + poziom

#------------------------------------------
# Leczenie za pomocą soku
def heal():
    global zycie
    if ekwipunek["Sok"] > 0:  # Sprawdzanie soku w zapasie
        ekwipunek["Sok"] -= 1  # Odejmowanie jednego soku
        ilosc_uzdrowienia = randint(15, 30)  # Powrót zdrowia
        zycie = min(zycie + ilosc_uzdrowienia, 100)  # Zdrowie nie może być powyżej 100
        print(f"Pijesz sok i odzyskujesz {ilosc_uzdrowienia} HP.")  # Wiadomość o powrocie zdrowia
    else:
        print("Nie masz soku!")  # Wiadomość, gdy sok się skończył

#--------------------------------------
# Zdobycie pieniędzy za pomocą herbaty
def restore_money():
    global pieniadze
    if ekwipunek["Herbata"] > 0:  # Sprawdzanie herbaty w zapasie
        ekwipunek["Herbata"] -= 1  # Odejmowanie jednej herbaty
        pieniadze = min(pieniadze + 30, 100)  # Nie może być powyżej 100
        print("Pijesz herbatę i odzyskujesz 30 zł.")  # Wiadomość o odzyskaniu pieniędzy
    else:
        print("Nie masz herbaty!")  # Wiadomość, gdy herbata się skończyła

#--------------------------------------------
# Wybór działań bohatera
def wybierz_atak():
    print("a/A - Zwykły atak")  # Zwykły atak
    print("b/B - Atak pomidorem!")  # Atak pomidorem
    print("s/S - Wypij sok")  # Powrót zdrowia za pomocą soku
    print("d/D - Wypij herbatę")  # Odzyskanie pieniędzy za pomocą herbaty
    print("q/Q - Poddaj się i zakończ grę")  # Zakończenie gry
    co = input().upper()
    if co == 'A':
        return zwykly_atak()
    elif co == 'B':
        return atak_pomidorem()
    elif co == 'S':
        heal()
        return 0
    elif co == 'D':
        restore_money()
        return 0
    elif co == 'Q':
        print("Zakończyłeś grę.")
        exit()
    else:
        print("Nie wybrano akcji")
        return 0

#------------------------------------------------
# Generacja losowego przeciwnika
def random_oponent():
    global lokacja
    if lokacja == "Start":
        przeciwnicy = [["Małe dziecko", 15, 3], ["Kasjer", 10, 3]]  # Przeciwnicy na początku
    elif lokacja == "Zabka":
        przeciwnicy = [["Pracownik", 25, 5], ["Człowiek, który dziwnie patrzy na ciebie", 20, 7]]  # Przeciwnicy w Żabce
    elif lokacja == "Biedronka":
        przeciwnicy = [["Dziwny kasjer", 50, 10], ["Znajomy", 40, 8]]  # Przeciwnicy w Biedronce
    else:
        przeciwnicy = [["Pracownik, który myśli, że kradniesz", 60, 12], ["Mama z dzieckiem", 100, 15]]  # Przeciwnicy w innym miejscu

    return choice(przeciwnicy)  # Losowanie przeciwnika

#-----------------------------------------------
# Zwiększenie poziomu bohatera
def level_up():
    global poziom, zycie, pieniadze, zbroja
    poziom += 1  # Zwiększenie poziomu
    zycie = min(zycie + 20, 100)  # Zwiększamy zdrowie
    pieniadze = min(pieniadze + 10, 100)  # Zdobijamy pieniądze
    zbroja = min(zbroja + 5, 50)  # Zwiększamy zbroję
    print(f"Gratulacje! Awansowałeś na {poziom} poziom!")

#---------------------------------------------------
# Zmiana lokacji
def zmiana_lokacji():
    global lokacja
    print("Gdzie chcesz iść?")
    if lokacja == "Start":
        print("1. Żabka")
        print("2. Biedronka")
    elif lokacja == "Zabka":
        print("1. Start (Wróć na początek)")
        print("2. Biedronka")
    elif lokacja == "Biedronka":
        print("1. Start (Wróć na początek)")
        print("2. Żabka")
    else:
        print("1. Wróć do Żabki")

    wybor = input("Wybierz numer: ")
    if wybor == "1":
        if lokacja == "Start":
            lokacja = "Zabka"
        elif lokacja == "Zabka":
            lokacja = "Biedronka"
        elif lokacja == "Biedronka":
            lokacja = "Start"
        else:
            lokacja = "Zabka"
    elif wybor == "2":
        if lokacja == "Start":
            lokacja = "Biedronka"
        elif lokacja == "Zabka":
            lokacja = "Start"
        elif lokacja == "Biedronka":
            lokacja = "Zabka"
        else:
            lokacja = "Start"
    print(f"Przemieszczasz się do {lokacja}")

# -----------------------------------

liczba_pokonanych_przeciwników = 0

while zycie > 0:
    print("\n" + "-" * 40)
    print(f"Jesteś w {lokacja}")  # Aktualna lokalizacja
    przeciwnik = random_oponent()  # Losowy przeciwnik
    print(f"Napotkałeś {przeciwnik[0]}!")  
    print(f"Przeciwnik ma {przeciwnik[1]} HP i zadaje Ci {przeciwnik[2]} obrażeń")  # Cechy przeciwnika

    while przeciwnik[1] > 0 and zycie > 0:  # Wszyscy żyją
        # Wyliczanie obrażeń
        obrazenia_otrzymane = max(przeciwnik[2] - zbroja, 0)  # Szkoda ze zbroją
        zycie -= obrazenia_otrzymane  
        print(f"Dostałeś {obrazenia_otrzymane} obrażeń. Twoja zbroja została obniżona do {zbroja}.")  

        if zycie <= 0:  # Jak zdrowie jest 0 i mniej
            print(f"Zostałeś pokonany przez {przeciwnik[0]}!")  
            print(f"{przeciwnik[0]} mówi: {choice(frazy_smierci)}") 
            break 

        # Wybór akcji
        print(f"Życie: {zycie}, Zbroja: {zbroja}") 
        obrazenia = wybierz_atak() 

        if obrazenia > 0:  # Atak
            przeciwnik[1] -= obrazenia 
            print(f"Zadałeś {obrazenia} obrażeń {przeciwnik[0]}. Teraz jego zdrowie wynosi {przeciwnik[1]}")  #

            # Jak działa zbroja
            if zbroja > 0:
                zbroja -= 2  # Zmniejsza się o dwa
                zbroja = max(zbroja, 0)  # Nie mniej niż zero
                print(f"Twoja zbroja została pomniejszona do {zbroja}.") 

        if zycie <= 0:  # Kiedy zdrowie jest równe albo mniejsze od zera
            print(f"Zostałeś pokonany przez {przeciwnik[0]}!")
            print(f"{przeciwnik[0]} mówi: {choice(frazy_smierci)}") 
            break 

        # Wybór akcji
        print(f"Życie: {zycie}, Zbroja: {zbroja}") 
        obrazenia = wybierz_atak()  

        if obrazenia > 0:  
            przeciwnik[1] -= obrazenia 
            print(f"Zadałeś {obrazenia} obrażeń {przeciwnik[0]}. Teraz jego zdrowie wynosi {przeciwnik[1]}") 
            
            if zbroja > 0:
                zbroja -= 2  # Zmniejsza się o dwa
                zbroja = max(zbroja, 0)  # Nie mniej niż zero
                print(f"Twoja zbroja została pomniejszona do {zbroja}.")  
        
        if przeciwnik[1] <= 0:
            print(f"Pokonałeś {przeciwnik[0]}!") 
            liczba_pokonanych_przeciwników += 1 
            doswiadczenie += 10 
            if doswiadczenie >= poziom * 100: 
                level_up() 
            break 

        # Jeśli przeciwnik żyje, atakuje bohatera
        obrazenia_przeciwnika = max(przeciwnik[2] - zbroja, 0)  # Przeciwnik zadaje obrażenia z uwzględnieniem zbroi bohatera
        zycie -= obrazenia_przeciwnika  # Zmniejszamy zdrowie bohatera
        print(f"{przeciwnik[0]} atakuje! Dostajesz {obrazenia_przeciwnika} obrażeń. Twoje zdrowie: {zycie}")  # Informacja o ataku przeciwnika

        # Logika zmniejszenia zbroi przy otrzymaniu obrażeń
        if zbroja > 0:
            zbroja -= 1  # Zbroja zmniejsza się o 1 przy otrzymaniu obrażeń
            zbroja = max(zbroja, 0)  # Zbroja nie może być mniejsza niż 0
            print(f"Twoja zbroja została zmniejszona do {zbroja}.")  # Wiadomość o zmniejszeniu zbroi

    # Jeśli bohater żyje, oferujemy wybór dalszych działań
    if zycie > 0:
        print(f"\nPokonałeś {przeciwnik[0]}! Liczba twoich zwycięstw: {liczba_pokonanych_przeciwników}")
        print(f"Zdobyłeś 10 doświadczenia. Poziom: {poziom}")
        print(f"Jesteś w {lokacja}.")
        print(f"Twoje zdrowie: {zycie}, Twoja zbroja: {zbroja}")  # Aktualny status zdrowia i zbroi
        zmiana_lokacji()  # Przejście do nowej lokacji lub wybór innych działań
    else:
        break  # Jeśli bohater zginął, wychodzimy z głównej pętli

print("Gra zakończona.")  # Wiadomość o zakończeniu gry
