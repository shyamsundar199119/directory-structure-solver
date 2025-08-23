# Directory Tree Parser API

A Spring Boot application to **upload, parse, and explore directory structures from a CSV file**.  

---

## Features
- Upload a CSV file and build a directory tree  
- Render the tree in a human-readable format  
- Filter files by classification (`SECRET`, `TOP_SECRET`)  
- Calculate total size of **PUBLIC** files  
- Retrieve non-public files under a given folder  
- Centralized error handling for parsing, filtering, and tree-building issues  

---

## ⚙Tech Stack
- **Java 8**  
- **Spring Boot**  
- **Maven**  

---

## API Endpoints
- `POST /api/directory/upload`  
- `GET /api/directory/tree`  
- `GET /api/directory/size/public`  
- `GET /api/directory/files/top-secret`  
- `GET /api/directory/files/secret`  
- `GET /api/directory/files/secret-or-top-secret`  
- `GET /api/directory/files/non-public?folderName={name}`  
