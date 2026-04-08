# 🏨 BookMyStayApp – Concurrent Booking Simulation

## 📌 Overview

**BookMyStayApp** is a Java-based simulation of a hotel booking system that demonstrates how **concurrent booking requests** are handled safely using multithreading and thread-safe data structures.

The system ensures:

* No overbooking of rooms
* Unique room allocation
* Safe concurrent execution using Java concurrency utilities

---

## 🚀 Features

* 🧵 **Concurrent Booking Handling** using `ExecutorService`
* 🔒 **Thread-Safe Inventory Management**
* 🆔 **Unique Room Allocation**
* 📊 **Real-time Booking Logs**
* ⚡ **Efficient Queue-less Parallel Processing**

---

## 🏗️ System Design

### Core Components

| Component         | Description                             |
| ----------------- | --------------------------------------- |
| `Reservation`     | Represents a booking request            |
| `RoomInventory`   | Manages room availability (thread-safe) |
| `BookingService`  | Processes bookings and allocates rooms  |
| `ExecutorService` | Simulates concurrent users              |

---

## 🧠 How It Works

1. Room inventory is initialized (e.g., Single, Double rooms)
2. Multiple booking requests are created
3. Requests are processed **in parallel using threads**
4. Inventory updates are synchronized to avoid race conditions
5. Successful bookings allocate unique room IDs
6. Failed bookings occur when rooms are unavailable

---

## 🔧 Technologies Used

* Java
* Multithreading
* `ExecutorService`
* `ConcurrentHashMap`
* `AtomicInteger`

---

## ▶️ How to Run

### 1. Clone the Repository

```bash
git clone https://github.com/VGAJI469/BookMyStayApp.git
cd BookMyStayApp
```

### 2. Compile

```bash
javac BookMyStayApp.java
```

### 3. Run

```bash
java BookMyStayApp
```

---

## 🧪 Sample Output

```
pool-1-thread-1 -> Amit booked S1
pool-1-thread-2 -> Priya booked S2
pool-1-thread-3 -> Failed Rahul
pool-1-thread-1 -> Neha booked D3

Final Inventory:
Single 0
Double 0

Allocations:
Single [S1, S2]
Double [D3]
```

---

## ⚠️ Concurrency Challenges Solved

| Problem         | Solution                 |
| --------------- | ------------------------ |
| Race Conditions | `synchronized` methods   |
| Data Corruption | `ConcurrentHashMap`      |
| Duplicate IDs   | `AtomicInteger`          |
| Overbooking     | Atomic inventory updates |

---

## 🌱 Future Enhancements

* 🔥 Booking cancellation & refunds
* 🔥 Priority/VIP booking queue
* 🔥 REST API with Spring Boot
* 🔥 Database integration (MySQL)
* 🔥 Distributed locking (Redis/Zookeeper)

---

## 🤝 Contributing

Contributions are welcome! Feel free to fork the repo and submit a pull request.

---

## 📄 License

This project is open-source and available under the MIT License.

---

## 👨‍💻 Author

Developed as part of a system design / concurrency simulation project.

---
