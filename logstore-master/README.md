# logstorage
Teste de candidatos para vagas de desenvolvedores para Cingo (www.cingo.com.br).

# Pré-requisitos para execução
1. Maven 3.6.3
2. Java 1.8
3. Apache Tomcat 9.
4. PostgreSQL 13

# Executar projeto
1. Baixar o projeto para disco local.
2. Ajustar os dados <local_path>\logstorage\src\main\resources\META-INF\persistence.xml e alterar para os dados de conexão para seu banco de dados local.
3. Acessar a pasta do projeto com cmd ou similar e executar a linha de comando "mvn clean install"
4. Acessar a pasta target criada na pasta do projeto e fazer o deploy do .war gerado na sua instalação local do Apache Tomcat 9
5. Para testar se a aplicação subiu corretamente acesse http://localhost:8080/logstore-0.0.1-SNAPSHOT/log e verifique se a resposta é um JSON similar ao abaixo: 

[{
  "id" : 2221,
  "content" : "org.hibernate.tool.hbm2ddl.TableMetadata: <init> foreign keys: []",
  "occurrences" : 299
}, {
  "id" : 2222,
  "content" : "org.hibernate.tool.hbm2ddl.TableMetadata: <init> indexes: [primary]",
  "occurrences" : 296
}, {
  "id" : 7508,
  "content" : "com.cingo.cingohc.sistemas.util.OuterQuery$2: execute Parametros aplicados: ",
  "occurrences" : 226
}, {
  "id" : 7510,
  "content" : "com.cingo.cingohc.sistemas.util.OuterQuery: getObjects Retornando 1 registros do SQL",
  "occurrences" : 176
}, {
  "id" : 2220,
  "content" : "org.hibernate.tool.hbm2ddl.TableMetadata: <init> columns: [neoid]",
  "occurrences" : 125
}]
