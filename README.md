# Administration-of-an-Epidemiologic-Products-Business
## **Etapa 1**

<br>

### **Tema proiectului**
Implementarea unui sistem de gestiune al unei firme de produse sanitare epidemiologice (masti si dezinfectanti). Mastile fabricate sunt chirurgicale si din policarbonat, iar dezinfectantii fabricati sunt impotriva bacteriilor, impotriva fungilor sau impotriva virusurilor.

Firma de produse sanitare necesita si o arhiva a tuturor comenzilor onorate si clientii cu care a colaborat (ex: spitale, farmacii, etc.). Comenzile pot fi simple sau personalizate, caz in care la pretul total apare o taxa de 50%. Pretul unui produs se calculeaza astfel:

- masca chirurgicala -> 10 lei
- masca policarbonat -> 20 lei
- dezinfectant cu eficienta $\le$ 90% -> 10 lei
- dezinfectant cu eficienta $\le$ 95% -> 20 lei
- dezinfectant cu eficienta $\le$ 97.5% -> 30 lei
- dezinfectant cu eficienta $\le$ 99% -> 40 lei
- dezinfectant cu eficienta $\le$ 99.99% -> 50 lei

De asemenea, pentru a calcula eficienta unui dezinfectant se folosesc formulele:

- Eficienta dez. antibacterian = $\frac{No.BacteriiUcise}{TotalBacterii (=10^9)}$ 
- Eficienta dez. antiviral = $\frac{No.VirusuriUcise}{TotalVirusuri (=10^8)}$ 
- Eficienta dez. antifungic = $\frac{No.FungiUcisi}{TotalFungi (=1.5*10^6)}$ 

<br>

### **Clasele modelului**
- **clasa Mask**: clasa abstracta folosita ca o baza in crearea celorlalte tipuri de masti.
- **clasa SurgicalMask**: clasa ce mosteneste clasa Mask si contine informatiile necesare unei masti chirurgicale.
- **clasa PolycarbonateMask**: clasa ce mosteneste clasa Mask si contine informatiile necesare unei masti din policarbonat.

<br>

- **clasa Sanitizer**: clasa abstracta folosita ca o baza in crearea celorlalte tipuri de dezinfectanti.
- **clasa BacteriaSanitizer**: clasa ce mosteneste clasa Sanitizer si contine informatiile necesare unui dezinfectant antibacterian.
- **clasa VirusSanitizer**: clasa ce mosteneste clasa Sanitizer si contine informatiile necesare unui dezinfectant antiviral.
- **clasa FungalSanitizer**: clasa ce mosteneste clasa Sanitizer si contine informatiile necesare unui dezinfectant antifungic.

<br>

- **clasa Aquisition**: clasa ce contine detalii referitoare la istoricul vanzarilor si clientii carora li s-a livrat comanda.
- **clasa Client**: clasa ce contine detalii referitoare la clientii firmei de produse sanitare.
- **clasa Address**: clasa ce contine detalii referitoare la adresa unui client.

<br>

In afara de clasele specificate mai sus, programul prezinta si un pachet **App** in care se pot gasi urmatoarele clase:
- **clasa Main**: instantiaza meniul si ruleaza programul principal al aplicatiei.
- **clasa Menu**: are implementata o metoda specifica meniului interactiv si instantiaza un obiect service (Clasa creata folosind un design pattern - singleton).
- **clasa Service**: are implementate toate metodele specificate in meniu (Clasa creata folosind un design pattern - singleton).
- **clasa Reader**: are implementate metodele de citire a datelor de la tastatura in scopul crearii de obiecte noi (Clasa creata folosind un design pattern - singleton).

<br>

### **Meniul aplicatiei**

<br>

```
-----------------------------------
ADMINISTRATION OF AN EPIDEMIC PRODUCTS BUSINESS - DAVID PATRANJEL 251
Here are all the actions you can choose from:
1. Add a mask.
2. List all the masks.
3. Delete a mask.
4. Add a sanitizer.
5. List all the sanitizers.
6. Delete a sanitizer.
7. Add a client.
8. List all the clients.
9. Add a acquisition.
10. List all the acquisitions.
11. Show income in a given month.
12. Show TVA (19% of income) for a given year.
0. Exit
-----------------------------------
```