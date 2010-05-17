
Troy Giunipero
17 May 2010

The AffableBean application requires access to a MySQL database.

Before running the application,

 1. Download MySQL from: http://dev.mysql.com/downloads/

    The database configuration uses 'root' / '' as the default username / password.
    This is used in the AffableBean project.

 2. From the IDE's Services window, right-click the MySQL Server
    node and choose Create Database.

 3. In the Create Database dialog, type in 'affablebean' and select
    the 'Grant Full Access To *@localhost' option.

 4. Click OK to exit the dialog.

 5. Run the SQL scripts found in this directory. The affablebean_schema
    script creates tables necessary for the application. The affablebean_sample_data
    script adds sample data to the tables. Run the schema creation script first,
    then run the sample data script.

    a. Double-click each script node to open them in the IDE's editor.
    b. In the toolbar above the editor, make sure the connection to the
       'affablebean' database is selected:

        jdbc:mysql://localhost:3306/affablebean

    c. Click the Run SQL button to run the script.

 8. It is necessary to enable automatic driver deployment on the server.
    This is because the GF server doesn't contain the MySQL driver by default.
    Choose Tools > Servers, and select your server in the left pane. Then
    in the right pane, select the 'Enable JDBC Driver Deployment' option.

    After making this change, you'll need to restart the server (if it's
    already been started).


Notes:

    The sun-resources.xml file creates the 'jdbc/affablebean'
    data source, and 'AffablebeanPool' connection pool on the
    server when the application is deployed.

    *The server may need to be restarted for the data source and
    connection pool settings to take effect.*

    The application uses EclipseLink as the persistence provider, and
    is being developed using NetBeans 6.8 and 6.9 dev with GlassFish v3.