# Tarefa A1

### Disciplina de Engenharia de Software - Ciência da Computação - UniRitter FAPA

Sistema bancário com para cadastro de conta, saques, depósitos, extratos e transferências.<br>

Para rodar o código execute (Windows):

```shell
& 'C:\Program Files\Java\jre1.8.0_341\bin\java.exe' '-cp' 'C:\Users\${USER}\AppData\Local\Temp\cp_76t2pp7ju8z9r8wwfwcq80iec.jar' 'com.berjooj.Main'
```

## Dependências

-   [GSON](https://github.com/google/gson/blob/master)
    -   Gradle:
        ```gradle
        dependencies {
            implementation 'com.google.code.gson:gson:2.10.1'
        }
        ```
    -   Maven:
        ```XML
            <dependency>
                <groupId>com.google.code.gson</groupId>
                <artifactId>gson</artifactId>
                <version>2.10.1</version>
            </dependency>
        ```
    -   [Gson jar downloads](https://maven-badges.herokuapp.com/maven-central/com.google.code.gson/gson)

### <b>Obs</b>
* resources/bancos.json contém um carga de dados inicial
    * Todas as agências são 1
    * Número de contas de 0 a 4
    * Senha padrão 123

<b>Bernardo Moreira, 2023</b>
