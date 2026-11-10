# InstantDoctor247 🩺

An on-demand healthcare, telehealth video consultation, and clinic booking platform built faithfully from the Figma design system.

---

## 🏗️ Architecture

- **Frontend**: Vite + React 18 + Tailwind CSS + Lucide Icons + React Router
  - Matches Figma components: Patient Portal, Doctor Portal, Top Doctors carousel, Instant Video Consultation, Book Appointments, Health Articles, and Authentication screens.
- **Backend**: Spring Boot 3.2 (Java 17) + Spring Web + Spring Data JPA + H2 In-Memory Database
  - RESTful APIs for Doctors, Appointments, and Articles with pre-seeded data.

---

## 🚀 Running the Project

### 1. Backend (Spring Boot)
Requires JDK 17 and Maven.

```bash
cd backend
mvn spring-boot:run
```
- API Base URL: `http://localhost:8080/api`
- H2 Web Console: `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:instantdoctordb`, User: `sa`, Password: *empty*)

#### Key Endpoints:
- `GET /api/doctors` - List specialists (supports `?search=`, `?specialty=`, `?instant=true`)
- `GET /api/doctors/{id}` - Doctor detail & bio
- `GET /api/appointments` - List consultations (supports `?status=UPCOMING`)
- `POST /api/appointments` - Book a slot (Online Video / Clinic)
- `GET /api/articles` - Health articles & news

---

### 2. Frontend (Vite + React)
```bash
cd frontend
npm run dev
```
Open [http://localhost:5173](http://localhost:5173) in your browser.

---

## 📱 Features & Screens

1. **Patient Home (`/`)**:
   - Greeting banner with user profile (`Hello! Vedant Tidke`)
   - Search bar for doctors, symptoms, clinics
   - 24/7 Telehealth promo hero card
   - Quick action cards: **Book Appointment** & **Instant Consultation**
   - Doctor Specialty horizontal filter chips
   - Top Doctors carousel (Dr. Crick, Dr. Lachinet, Dr. Strain, Dr. Sarah Jenkins)
   - Health Articles carousel (COVID-19 Vaccine & Boosters, Heart health tips)

2. **Find a Doctor (`/doctors`)**:
   - Filter by specialty or toggle "Instant Video"
   - Specialty badges, ratings, fees, and location
   - Direct slot booking and instant video call launch

3. **Doctor Profile & Booking (`/doctors/:id`)**:
   - Experience, patient count, reviews rating
   - Mode selector: **Online Video** vs. **Clinic Visit**
   - Date picker & time slot selector
   - Payment method selection & instant booking confirmation ticket

4. **Appointments Management (`/appointments`)**:
   - Filter tabs: `Upcoming`, `Completed`, `Canceled`, `All`
   - Real-time waiting room button for video visits

5. **HD Telehealth Consultation Room (`/video-call/:id`)**:
   - Doctor HD video feed + Patient Picture-in-Picture
   - Mute, Video toggle, End call controls, call duration timer
   - Post-call review & feedback dialog

6. **Doctor Portal (`/doctor-portal`)**:
   - Doctor stats (Appointments, today's earnings, total patients)
   - Online/Offline toggle for instant patient matching
   - Patient waiting room launcher and EHR review

7. **Auth / Sign In / Sign Up (`/auth`)**:
   - Password eye toggle, remember me checkbox, Google/Facebook/Apple social buttons
