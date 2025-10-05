Of course. Here is a detailed documentation for your LinkLeap project, written in the standard format for a GitHub `README.md` file.

You can copy and paste this entire text into a new file named `README.md` in the root folder of your project.

-----

# LinkLeap: A Full-Stack URL Shortener

LinkLeap is a complete, full-stack URL shortening web application built from the ground up with Java, Spring Boot, and deployed on AWS. It provides a simple interface to convert long URLs into manageable, short links and redirects users to the original URL when the short link is accessed.

**Live Demo:** [**https://linkleap-anurag.ddns.net**](https://linkleap-anurag.ddns.net)

-----

## Features

* **URL Shortening:** Convert any valid, long URL into a unique, short key.
* **Redirect Service:** Automatically redirects short links to their original destination.
* **Web Interface:** A clean, user-friendly frontend built with Thymeleaf to create and view short links.
* **REST API:** A dedicated API endpoint for creating short links programmatically.
* **Server-Side Validation:** Robust input validation to handle errors gracefully and prevent invalid data from being processed.

-----

## Architecture Diagram

The application is deployed on AWS with a decoupled architecture, ensuring scalability and separation of concerns.

```
      User's Browser
            |
 (HTTPS on Port 443)
            |
┌───────────────────────────┐
│  AWS EC2 Instance         │
│ ┌───────────────────────┐ │
│ │  Nginx Reverse Proxy  │ │
│ └──────────┬────────────┘ │
│            | (Forwards   │
│            |  traffic)   │
│ ┌──────────┴────────────┐ │
│ │ Spring Boot Application │<───(JDBC on Port 5432)───┐
│ │   (Runs on Port 8080)   │                          │
│ └───────────────────────┘                          │
└───────────────────────────┘                          │
                                                       │
                                           ┌──────────────────────────┐
                                           │ AWS RDS Instance         │
                                           │ ┌──────────────────────┐ │
                                           │ │ PostgreSQL Database  │ │
                                           │ └──────────────────────┘ │
                                           └──────────────────────────┘
```

-----

##  Tech Stack

* **Backend:** Java 17, Spring Boot 3, Spring Web, Spring Data JPA, Hibernate
* **Frontend:** Thymeleaf, HTML5, CSS3
* **Database:** PostgreSQL
* **Cloud & Deployment:**
    * **AWS (Amazon Web Services):** EC2 (for application server), RDS (for managed database), Security Groups (for firewall rules)
    * **Nginx:** As a reverse proxy to handle web traffic.
    * **Certbot & Let's Encrypt:** For free, automated SSL certificate generation (enabling HTTPS).
    * **Build Tool:** Apache Maven
    * **Server OS:** Ubuntu Linux

-----

##  Local Setup and Installation

To run this project on your local machine, follow these steps:

1.  **Prerequisites:**

    * Git
    * Java JDK 17 or later
    * Apache Maven
    * A local PostgreSQL server

2.  **Clone the Repository:**

    ```bash
    git clone <your-repository-url>
    cd <repository-folder>
    ```

3.  **Database Configuration:**

    * Start your local PostgreSQL server.
    * Create a new database (e.g., `linkleap_local`).
    * Open the `src/main/resources/application.properties` file.
    * Update the `spring.datasource` properties to point to your local database:
      ```properties
      spring.datasource.url=jdbc:postgresql://localhost:5432/linkleap_local
      spring.datasource.username=your_postgres_username
      spring.datasource.password=your_postgres_password
      spring.jpa.hibernate.ddl-auto=update
      ```

4.  **Run the Application:**

    * You can run the application using Maven:
      ```bash
      mvn spring-boot:run
      ```
    * Alternatively, you can run the main application class (`ApiApplication.java`) from your IDE.

5.  **Access the Application:**
    The application will be available at `http://localhost:8080`.

-----

##  Deployment Process

The application is deployed on AWS following these key steps:

1.  **Infrastructure Provisioning:** An **EC2 `t2.micro` instance** was launched to host the application, and a **PostgreSQL RDS instance** was provisioned for the database.
2.  **Networking:** **AWS Security Groups** were configured to act as a firewall, allowing public traffic on ports 80 (HTTP) and 443 (HTTPS), while restricting database access to only the EC2 instance.
3.  **Build & Transfer:** The application was packaged into an executable `.jar` file using Maven. This file was then securely copied to the EC2 server using `scp`.
4.  **Reverse Proxy:** **Nginx** was installed on the EC2 server and configured to act as a reverse proxy. It forwards all incoming public traffic on ports 80/443 to the Spring Boot application running internally on port 8080.
5.  **HTTPS/SSL:** **Certbot** was used to automatically obtain and install a free SSL certificate from **Let's Encrypt**, enabling secure HTTPS traffic and setting up automatic redirects from HTTP.
6.  **Application Execution:** The Spring Boot `.jar` file is run as a persistent background service using `nohup`.

-----

##  Future Improvements

* Containerize the application with **Docker** for easier and more consistent deployments.
* Set up a **CI/CD pipeline** (e.g., with GitHub Actions) to automate the build and deployment process.
* Add **user authentication** using Spring Security to allow users to manage their own links.
* Implement link click **analytics** to track how many times a short link is used.
* Add a custom caching layer with **Redis** to improve redirect performance.
