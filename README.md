
# Chat-App
## Staring the Application
Clone the repository to your computer
## Step 1 npm
open src/main/messenger-app-react in your terminal and write
```
npm install && npm run build
```

## Step 2 Docker

This project is built on Microsoft SQL Server.

To run a SQL Server instance in a Docker container, use the following command:

```
docker run -e "ACCEPT_EULA=Y" -e "MSSQL_SA_PASSWORD=yourStrong(!)Password" -p 1433:1433 -d mcr.microsoft.com/mssql/server:2022-latest
```
Replace <yourStrong(!)Password> with a strong password of your choice.

## Step 3 Update application.properties
Update the application.properties file with your password
```application.properties
dataSource.url=jdbc:sqlserver://localhost:1433;encrypt=true;trustServerCertificate=true
dataSource.username=sa
dataSource.password=Insert your password here
```

## Step 4 Start the main class
Run the MessageAppServer.java class


