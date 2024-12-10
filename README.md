**CodeMirror**

CodeMirror is a Java Swing-based text editor designed to provide developers with essential text editing features such as file handling, syntax search, undo/redo, and tab management. Built with Java Swing and focused on usability, CodeMirror enhances coding workflows by bringing IDE-like features to developers.

📋 **Table of Contents**

Overview
Features
Getting Started
Installation
Usage
Project Structure
CI/CD Pipeline
Contributing
License

🚀 **Overview**

CodeMirror is a developer productivity tool built using Java Swing. This application offers an intuitive user interface with capabilities like tab-based editing, file handling, undo/redo actions, and syntax search, facilitating efficient coding. The project follows Scrum principles for agile development, prioritizing flexibility, collaboration, and continuous improvement.

✨ **Features**

CodeMirror provides the following features:

File Management:
Open, save, and switch between multiple files using tab-based navigation.

Undo/Redo History:
Easily undo and redo changes using a history system.

Search Capabilities:
Allows users to search for selected text in Google directly.

Syntax & Theme Support:
Toggle between light/dark themes for improved accessibility.

Tabbed Interface:
Users can switch between multiple open files through a tabbed UI.

Line Numbers:
Toggle the visibility of line numbers for better code comprehension.

🏗️ **Getting Started**

To use this project, follow the steps below to set up CodeMirror on your system.

💻 **Installation**

Prerequisites
Make sure you have the following installed:
Java Development Kit (JDK): Java 8 or higher
Maven: Dependency manager

**Steps to Run the Application**


1. Clone Repository:
git clone https://github.com/mefigeniah/CodeMirror.git

2. Navigate to the directory:
cd CodeMirror

3. Build the project:
Use Maven to build the application.
mvn clean compile

4. Run the application:
Use Maven's exec:java to run the main application.
mvn exec:java -Dexec.mainClass="com.codemirror.Main"

⚙️ **Usage**

Once the application is running, you'll be greeted with the editor's graphical interface.

Actions you can perform:
Open files: File > Open
Save files: File > Save
Copy: Ctrl+C
Cut: Ctrl+X
paste: Ctrl+V
Undo/Redo changes:  Edit > Undo to undo and Edit > Do to redo.
Search within the app or on Google: Highlight a word and Edit > Search in Google.
Switch between tabs: Click on the tabs at the top.

🛠️**CI/CD Pipeline**

We use GitLab CI/CD for automating testing and deployment. The pipeline contains the following stages:

Build: Compiles the source code into a usable application.
Unit Tests: Runs Java unit tests to ensure functionality.
Lint Tests: Ensures code adheres to the coding standards.
Deploy: Prepares and deploys builds for production environments.
You can find the GitLab configuration at .gitlab-ci.yml.

📜 **License**

This project is licensed under the MIT License.
See the LICENSE file for details.

If you have further questions or need assistance, feel free to open an issue on the GitHub Issues page.

Happy coding! 🚀👩‍💻👨‍💻
