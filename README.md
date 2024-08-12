<br/>
<div align="center">
<a href="https://github.com/ShaanCoding/makeread.me">
<img src="src/resource/ravana_logo2.png" alt="Logo" width="229" height="199">
</a>
<h3 align="center">RAVANA ADVENCTURE PARK MANAGEMENT SYSTEM</h3>
<p align="center">
Java Based Information Management System
<br/>
<br/>
<a href="https://github.com/RAVANA-Final-Project/RavanaAPMS"><strong>Explore the docs »</strong></a>
<br/>
<br/>
<a href="https://github.com/RAVANA-Final-Project/RavanaAPMS/">View Repo .</a>  
<a href="https://github.com/RAVANA-Final-Project/RavanaAPMS/issues/new?labels=bug&amp;template=bug_report.md">Report Bug .</a>
<a href="https://github.com/RAVANA-Final-Project/RavanaAPMS/issues/new?labels=enhancement&amp;&template=feature_request.md">Request Feature</a>
</p>
</div>

<div align="center">
  <a href="https://github.com/RAVANA-Final-Project/RavanaAPMS/stargazers"><img src="https://img.shields.io/github/stars/RAVANA-Final-Project/RavanaAPMS" alt="Stars Badge"/></a>
  <a href="https://github.com/RAVANA-Final-Project/RavanaAPMS/network/members"><img src="https://img.shields.io/github/forks/RAVANA-Final-Project/RavanaAPMS" alt="Forks Badge"/></a>
  <a href="https://github.com/RAVANA-Final-Project/RavanaAPMS/pulls"><img src="https://img.shields.io/github/issues-pr/RAVANA-Final-Project/RavanaAPMS?color=2b9348" alt="Pull Requests Badge"/></a>
  <a href="https://github.com/RAVANA-Final-Project/RavanaAPMS/issues"><img src="https://img.shields.io/github/issues/RAVANA-Final-Project/RavanaAPMS?color=2b9348" alt="Issues Badge"/></a>
  <a href="https://github.com/RAVANA-Final-Project/RavanaAPMS/graphs/contributors"><img alt="GitHub contributors" src="https://img.shields.io/github/contributors/RAVANA-Final-Project/RavanaAPMS?color=2b9348"></a>
  <a href="https://github.com/RAVANA-Final-Project/RavanaAPMS/blob/master/LICENSE"><img src="https://img.shields.io/github/license/RAVANA-Final-Project/RavanaAPMS?color=2b9348" alt="License Badge"/></a>
</div>

## Table of Contents

- [📖 Introduction](#introduction)
- [✨ Features](#features)
- [👥 Roles and Responsibilities](#roles-and-responsibilities)
  - [👨‍💼 Admin](#admin)
  - [👑 Super Admin](#super-admin)
  - [🔧 Equipment Manager](#equipment-manager)
- [⚙️ Installation](#installation)
- [🚀 Usage](#usage)
- [🤝 Contributing](#contributing)
- [📜 License](#license)
- [📞 Contact](#contact)

## Introduction

The Ravana Adventure Park Management System is a standalone application aimed at optimizing the operations of our adventure park. The software supports various user roles, each with specific responsibilities to ensure smooth and efficient park management.

<div>
  <img src="https://github.com/RAVANA-Final-Project/RavanaAPMS/blob/main/assets/Ravana-Intro.gif" alt="Example GIF" style="border-radius: 10px; margin: 10px;">
</div>

## Features

- **👤 User Management**: Efficient management of users, roles, and permissions.
- **📅 Activity Management**: Comprehensive scheduling and management of adventure activities and leadership programs.
- **🛠️ Equipment Management**: Detailed tracking of equipment inventory, maintenance, and usage.
- **🛍️ Order and Payment Management**: Handling of orders, offers, and payments with invoice generation.
- **👔 Employee Management**: Management of employee details, job roles, and attendance.
- **📊 Report Generation**: Extensive reporting capabilities to analyze different aspects of park operations.
- **🔒 Secure Access**: Role-based access control for data security and integrity.

## Roles and Responsibilities

### Admin 👨‍💼

The Admin role includes:

- **🔐 Login/Logout**: Secure authentication.
- **👥 Manage Users**: Register and update user information.
- **📦 Manage Order**: Add, update, delete orders, and manage offers.
- **🏃 Manage Activity**: Add new activities, update existing ones, and set activities as inactive.
- **💳 Manage Payments**: Generate and handle invoices.
- **👔 Manage Employees**: Add, update, and set employees as inactive.
- **📋 Manage Employee Positions**: Add job roles and manage vacancies.
- **📈 Generate Reports**:
  - Income Reports (Daily, Monthly, Yearly)
  - Employee Reports
  - User/Customer Reports

### Super Admin 👑

The Super Admin role includes:

- **🔐 All Admin Privileges** (excluding managing users, activities, and employees)
- **👨‍💼 Manage Admins**: Oversee admin accounts.
- **🔧 Manage Equipment Managers**: Oversee equipment manager accounts.
- **📊 View Reports**: Access various reports.
- **📈 Generate Reports**:
  - Invoices
  - Orders
  - Income
  - Attendance
  - Employee
  - Customer
  - Equipment
  - Outdated Equipment and Tools

### Equipment Manager 🔧

The Equipment Manager role includes:

- **🔧 Register Equipment**: Add, update, and manage equipment details.
- **🏷️ Manage Brand**: Add, update, and manage brand information.
- **📦 Manage Stock**: Add new stock, manage existing stock, and remove stock.
- **📈 Generate Reports**:
  - Outdated Equipment Report (Monthly)

### Built With

This project was built with the following technologies:

- [Java](https://nextjs.org/)
- [MySQL](https://react.dev/)
- [JFreeChart](https://www.jfree.org/jfreechart/)
- [FlatLaf](https://www.formdev.com/flatlaf/)
- [Jaspersoft](https://www.jaspersoft.com/)

## Installation ⚙️

To install RAPMS, follow these steps:

1. **📥 Download the setup file from the repository's releases page:**

    [Download Setup File](https://github.com/RAVANA-Final-Project/RavanaAPMS/releases/download/v1.0.0/Setup.exe)

2. **🛠️ Run the setup file to install the application directly.**

3. **📥 Download the SQL backup file from the repository:**

    [Download SQL Backup File](https://github.com/RAVANA-Final-Project/RavanaAPMS/releases/download/v1.0.0/Database_backup.sql)

4. **🔄 Restore the SQL backup file:**

    Open your MySQL client and run the following command to restore the database:

    ```sql
    mysql -u yourusername -p yourdatabase < path/to/your/sqlbackupfile.sql
    ```
## Project Setup ⚙️

To set up the project locally using NetBeans or Eclipse, follow these steps:

1. **📥 Clone the repository:**

    - Open NetBeans or Eclipse.
    - Navigate to `File` > `Import` > `Git` > `Projects` from Git (with Smart Import).
    - Select `Clone URI`, and enter the repository URL: https://github.com/RAVANA-Final-Project/RavanaAPMS.git
    - Choose a local directory where you want to clone the project and click `Next`.
    - Select the desired branch (usually main or master) and click `Finish`.

2. **🛠️ Open the Project:**

    - In NetBeans: Go to `File` > `Open Project` and navigate to the directory where you cloned the repository. Select the `project` and click `Open Project`.
    - In Eclipse: The project should automatically appear in the Project Explorer after cloning. If not, go to `File` > `Open Projects` from File System, browse to the cloned directory, and click `Finish`.

3. **▶️ Run the Project:**

    - In NetBeans: `Right-click` on the `project` in the `Projects pane` and select `Run`.
    - In Eclipse: `Right-click` on the `project` in the `roject Explorer` and select `Run As` > `Java Application`.

4. **✅ Verify Installation:**

    - The application should launch, and you can start using the Ravana Adventure Park Management System.

5. **💻 Additional Setup (Optional):**

    - If your project requires additional configurations (e.g., environment variables, additional libraries), make sure to set those up before running the project.

## Usage 🚀

After setting up the application, log in with the appropriate credentials based on your role (Admin, Super Admin, or Equipment Manager). Navigate through the dashboard to access the various functionalities provided for your role.

### Admin Guide 👨‍💼

- **👥 User Management**: Add, edit, or remove users.
- **📦 Order Management**: Manage orders and offers.
- **🏃 Activity Management**: Schedule new activities and view upcoming events.
- **💳 Payment Handling**: Generate and manage invoices.
- **👔 Employee Management**: Add and manage employee details and job roles.
- **📈 Report Generation**: Generate income, employee, and customer reports.

### Super Admin Guide 👑

- **🔧 System Oversight**: Manage admins and equipment managers.
- **📊 Report Access**: View and generate detailed reports.
- **📅 Attendance Tracking**: Generate employee attendance reports.

### Equipment Manager Guide 🔧

- **🔧 Equipment Registration**: Add, update, or remove equipment.
- **🏷️ Brand Management**: Manage equipment brands.
- **📦 Stock Management**: Handle stock details and inventory.
- **📈 Report Generation**: Generate monthly outdated equipment reports.

## Contributing 🤝

We welcome contributions from the community! If you would like to contribute to this project, please follow these steps:

1. **🍴 Fork the repository.**
2. **🔀 Create a new branch.**
3. **🛠️ Make your changes.**
4. **📥 Submit a pull request.**

Please ensure that your contributions align with the project's coding standards and guidelines.

## License 📜

This project is licensed under the [MIT License](LICENSE).

## Contact 📞

For any questions or further information, please contact us at:

- **✉️ Email**: dulanjayawebs@gmail.com
- **💬 Project Discussion**: [Talk](https://github.com/RAVANA-Final-Project/RavanaAPMS/discussions)

Thank you for using the Ravana Adventure Park Management System! 🌟
