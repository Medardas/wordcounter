# Word Counter

Aplikacija skirta parodyti minimnalius gebėjimus naudojant Java EE.
Jos tikslas yra asinchroniškai nuskaityti neribotą kiekį tekstinių (.txt) failų iki 1024KB ir išvesti juos į ekraną suskirstytus pagal pirmą raidę bei sukurti failus pagal tokį patį skirstymą.  

Pagrindiniai framework'ai panaudoti:
 * Spring MVC (kaip Spring Boot Web Starter)
 * Thymeleaf
 * JUnit
 * Mockito
 * Spring Devtools - lengviasniam darbui
 
Aplikacija statant buvo sukurta su minimaliu user fault apsaugų kiekiu ir bendrai su nedideliu funkcijų kiekiu. Tam pagrindinė priežastis yra tai, kad nenorėjau perdaug plėstis (funkcines ir ne-funkcines galimybes galima pildyti iki begalybės, tokias kaip gražesnis UI, apsauga nuo visokių failų pridėjimų ir priimamų failų tipų įvairovė, žodžių rikiavimas ir t.t. ) ir sąlygos buvo pakankamai aiškios, pavyzdžiui: bus paduodami tik tekstiniai failai, failuose bus tik angliški žodžiai (nors dėl to įdėjau paprastą regexo tikrinimą nes tai buvo greitas sprendimas). Taip pat egzistuoja vienas unit testas parašytas pilnai dėl įdėjos.

Dockerio integracija nepridėta nes neturiu daug profesionalios patirties su juo ir pamaniau, kad pagal gidą vistiek betkas galėtų sukurti paprastą image todėl nelabai būčiau daug pasiekęs parodydamas savo galimybes. 