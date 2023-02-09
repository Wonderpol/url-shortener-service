<a name="readme-top"></a>

<h3 align="center">Url shortener backend service</h3>

<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li><a href="#Usage - available endpoints">Usage</a></li>
    <li><a href="#TODO">TODO</a></li>
  </ol>
</details>



<!-- ABOUT THE PROJECT -->

## About The Project

Server layer of an application that gives and opportunity to shorten url.
The application solves problem with long unreadable links.
In the next step it will allow to collect statistics about shorten link

Here's why:

* Some users are scared of long links
* Easy link management
* Keep track of link statistics

The project is still in development due to that some features may not be available.

<p align="right">(<a href="#readme-top">back to top</a>)</p>

### Built With

The application is built with Java and Spring boot framework, Postgresql database and Docker.
For authentication, it uses JWT bearer tokens.

* [![Java][Java]][Java-url]
* [![Spring-boot][Spring-boot]][Spring-boot-url]
* [![Postgres][PostgreSQL]][PostgreSQL-url]

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- GETTING STARTED -->

## Getting Started

To setup and run the project follow instructions below.

### Prerequisites

* Docker
    * To install docker you can use some kind of package manager like `sudo apt install docker` - linux
      or `brew install docker` - macos
    * On windows go to docker website and download
      installer: [Docker installer](https://docs.docker.com/engine/install/)

### Installation

1. Clone the repo
   ```shell
   git clone https://github.com/Wonderpol/url-shortener-service.git
   ```
2. Open the following file: `src/main/resources/application-prod.yml`
   ```sh
   vim src/main/resources/application-prod.yml
   ```
   or open it in different text editor.
3. In config section enter your front end url and jwt secret key. The secret key can be generated
   using [Allkeysgenerator](https://www.allkeysgenerator.com).
   In the mail section provide your SMTP provider, email, and password.

    ```yaml
    config:
      frontend-url: FRONT_END_URL
      jwt:
        secret-key: JWT_SECRET_KEY
    
    spring:
      datasource:
        url: jdbc:postgresql://db:5432/urlshortenerdb
        username: admin
        password: password
        driver-class-name: org.postgresql.Driver
      jpa:
        hibernate:
          ddl-auto: update
        show-sql: true
        properties:
          hibernate:
            format_sql: false
        database: postgresql
        database-platform: org.hibernate.dialect.PostgreSQLDialect
      mail:
        host: smtp.gmail.com
        port: 587
        username: YOUR_EMAIL
        password: YOUR_PASSWORD
        properties:
          mail:
            smtp:
              auth: true
              starttls:
                enable: true
            debug: true
            protocol: smtp
            test-connection: false
     ```

4. Now within the root folder open terminal and type:
    ```shell
    docker-compose up
    ```

Now if everything went well the application will be available at: `localhost:8080`
<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- USAGE EXAMPLES -->

## Usage - available endpoints

All the available endpoints are listed below:

- Authentication:
    - POST `api/v1/auth/register` with body:<br></br>
  ```json
    {
        "email": "piaskowyjasiek@gmail.com",
        "password": "password",
        "name": "Jan",
        "lastName": "Piaskowy"
    }
    ```
    - POST `api/v1/auth/confirm-email?token=CONFIRM_EMAIL_TOKEN`
    - POST `api/v1/auth/authenticate`
  ```json
    {
      "email": "piaskowyjasiek@gmail.com",
      "password": "password"
    }
    ```

**Other endpoints will be published shortly**

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- TODO -->

## TODO

- [ ] Add all available endpoints to README
- [x] Add reset password feature
- [ ] Add link statistics
- [ ] Add admin role
- [ ] Add notification 30 days before link expire
- [ ] Add link to advert that will be shown before link redirect
- [ ] Multi-language Email Support
    - [ ] Polish

See the [open issues](https://github.com/Wonderpol/url-shortener-service/issues) for a full list of proposed features (
and known issues).

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- CONTRIBUTING -->

## Project status

**Project is still in development**

<p align="right">(<a href="#readme-top">back to top</a>)</p>


<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->

[Java]: https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white

[Java-url]: https://www.java.com/pl/

[Spring-boot]: https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white

[Spring-boot-url]: https://spring.io/

[PostgreSQL]:    https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white

[PostgreSQL-url]: https://www.postgresql.org/