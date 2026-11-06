# BizFlow – Offline-First Manufacturing & Distribution ERP 🏭📦

![Android](https://img.shields.io/badge/Platform-Android-green.svg)
![Language](https://img.shields.io/badge/Language-Java_11-blue.svg)
![UI Framework](https://img.shields.io/badge/UI-Material_Design_3-purple.svg)
![Min SDK](https://img.shields.io/badge/Min_SDK-24_(Android_7.0)-orange.svg)
![License](https://img.shields.io/badge/License-MIT-brightgreen.svg)

**BizFlow** is an offline-first Enterprise Resource Planning (ERP) mobile application tailored for manufacturing plants and warehouse distribution centers. Designed with high-contrast UI/UX, robust input validation, smooth animations, and modular architecture, BizFlow empowers warehouse managers and plant supervisors to track inventory stock, record manufacturing outputs, manage sales dispatches, and receive low-stock alerts without requiring constant internet connectivity.

---

## 🚀 Key Features & Architectural Modules

### 1. 🔐 High-Contrast Authentication System
- **Login Screen (`LoginActivity`):**
  - High-contrast text input fields (`#0F172A`) for effortless readability across all lighting environments.
  - Show/Hide password toggle (`app:endIconMode="password_toggle"`).
  - Direct links to **Forgot Password** recovery and **Create Account** registration.
- **Account Registration (`RegisterActivity`):**
  - Full Name, Email, Phone Number, Password, and Confirm Password fields.
  - Validation for non-empty input, valid email format regex (`Patterns.EMAIL_ADDRESS`), password length (≥ 6 characters), and password matching.
- **Password Reset (`ForgotPasswordActivity`):**
  - Email verification and automated password reset confirmation workflow.

---

### 2. 📊 Interactive ERP Business Dashboard
- **Real-Time Key Performance Indicators (KPIs):**
  - **Total Inventory Card:** Tracks total stock units and category counts.
  - **Month-to-Date Sales Card:** Displays total revenue and percentage growth.
  - **Production Overview Card:** Monitors daily manufacturing output batches.
  - **Low Stock Alert Banner:** Highlighted amber banner listing items below safety threshold (e.g. Steel Bearings, Industrial Bolts).
- **Quick Shortcuts:** Instant buttons to open Inventory, Production, or launch `AddProductActivity`.

---

### 3. 🧭 Dual Navigation System (Drawer + Bottom Nav + Toolbar)
- **Top Material Toolbar (`MaterialToolbar`):**
  - Customized with BizFlow branding title and overflow options menu (**Home**, **Inventory**, **Production**, **Sales**, **Settings**, **Logout**).
- **Navigation Drawer (`DrawerLayout` + `NavigationView`):**
  - Slide-out drawer with branded header displaying user profile (`admin@bizflow.com`).
  - Menu options: **Dashboard**, **Inventory**, **Purchase**, **Production**, **Sales**, **Warehouse**, **Reports**, **Settings**, and **Logout**.
- **Bottom Navigation Bar (`BottomNavigationView`):**
  - Persistent bottom tab bar enabling seamless switching between **Dashboard**, **Inventory**, **Production**, and **Sales** fragments.

---

### 4. 📦 Advanced Inventory Management & UI Components
- **`RecyclerView` & Custom `ProductAdapter`:**
  - Displays product items with ID, Name, Quantity badge, Category pill badge, CheckBox, Info button, and Delete button.
- **Real-Time `SearchView`:** Filter products instantly by product name, ID, or category as you type.
- **Category Filter `Spinner`:** Filter items by dropdown category (*All Categories*, *Raw Material*, *Finished Goods*, *Packaging*).
- **`CheckBox` Status Tracking:** Mark products as verified with real-time feedback Toasts.
- **`AlertDialog` Deletion Confirmation:** Displays a confirmation alert modal before removing an item from inventory.
- **`CustomAlertDialog` Details Modal:** Formatted custom dialog (`dialog_product_details.xml`) enabling viewing and editing product details.

---

### 5. 🔄 Inter-Component Data Passing
- **Activity to Fragment Data Passing (`AddProductActivity` ➔ `InventoryFragment`):**
  - Uses `ActivityResultLauncher` (`StartActivityForResult`) to capture new product submissions from `AddProductActivity` and insert them directly into `InventoryFragment`'s live list.
- **Fragment to Fragment Data Passing (`InventoryFragment` ➔ `ProductionFragment`):**
  - Selecting a stock item in `InventoryFragment` packs its details into a `Bundle` via `ProductionFragment.newInstance(...)`. `ProductionFragment` unpacks the arguments, shows a green **Received Data** banner, and pre-fills the production batch form.

---

### 6. ✨ Smooth UI Animations (5 Animation APIs)
1. **Fade In (`res/anim/fade_in.xml`):** Applied to dashboard cards and container views.
2. **Slide In Right (`res/anim/slide_in_right.xml`):** Smooth transition during fragment navigation.
3. **Scale Up (`res/anim/scale_up.xml`):** Bouncy scale entrance for branding logos and action buttons.
4. **Rotate Gear (`res/anim/rotate_gear.xml`):** Rotation animation resource for gear and status icons.
5. **Staggered Fall Down (`res/anim/layout_animation_fall_down.xml`):** Staggered vertical entrance animation for `RecyclerView` list items.

---

## 🛠️ Technology Stack & Libraries

- **Language:** Java 11
- **UI Framework:** Android XML Layouts & Material Design 3 Components (`com.google.android.material`)
- **Container Architecture:** Single-Activity host (`MainActivity`) with Fragments + Dedicated Auxiliary Activities
- **List Rendering:** `androidx.recyclerview.widget.RecyclerView` with custom `ProductAdapter` & `Filterable` implementation
- **Dialogs & Views:** `AlertDialog`, `CustomAlertDialog`, `SearchView`, `Spinner`, `CheckBox`, `ImageButton`, `MaterialCardView`, `MaterialButton`, `TextInputLayout`, `TextInputEditText`
- **Minimum SDK:** 24 (Android 7.0 Nougat) | **Target SDK:** 37

---

## 📂 Project Directory Structure

```
BizFlow-ERP/
├── app/
│   ├── src/main/
│   │   ├── java/com/example/bizzfloww/
│   │   │   ├── MainActivity.java           # Main container hosting Toolbar, Drawer & Bottom Nav
│   │   │   ├── LoginActivity.java          # Login activity with field validation
│   │   │   ├── RegisterActivity.java       # User registration activity
│   │   │   ├── ForgotPasswordActivity.java # Password recovery activity
│   │   │   ├── AddProductActivity.java     # Product entry form returning result to Inventory
│   │   │   ├── DashboardActivity.java      # Legacy standalone activity
│   │   │   ├── InventoryActivity.java      # Standalone inventory view
│   │   │   ├── ProductionActivity.java     # Standalone production view
│   │   │   ├── Product.java                # Inventory product data model
│   │   │   ├── ProductAdapter.java         # RecyclerView adapter with search filter & click callbacks
│   │   │   ├── InventoryItem.java          # Inventory item data model
│   │   │   ├── InventoryAdapter.java       # Adapter for stock entries
│   │   │   ├── ProductionItem.java         # Production log data model
│   │   │   ├── ProductionAdapter.java      # Adapter for production history
│   │   │   ├── DashboardFragment.java      # ERP business overview fragment
│   │   │   ├── InventoryFragment.java      # Live inventory stock list fragment
│   │   │   ├── ProductionFragment.java     # Manufacturing production log fragment
│   │   │   └── [Sales/Purchase/Warehouse/Reports/Settings]Fragment.java
│   │   └── res/
│   │       ├── anim/                       # Animation XMLs (fade, slide, scale, rotate, layout fall down)
│   │       ├── drawable/                   # Vector drawables & background shape badges
│   │       ├── layout/                     # Activity, Fragment, Dialog, and Item XML layouts
│   │       ├── menu/                       # Toolbar options menu, bottom nav menu & drawer menu
│   │       └── values/                     # colors.xml, strings.xml, themes.xml (high-contrast inputs)
│   └── build.gradle.kts
├── .gitignore
├── build.gradle.kts
└── settings.gradle.kts
```

---

## 📲 How to Build & Run in Android Studio

1. **Clone the Repository:**
   ```bash
   git clone https://github.com/code-with-maira/BizFlow-ERP.git
   ```
2. **Open in Android Studio:**
   - Launch **Android Studio**.
   - Select **Open an Existing Project** and browse to the cloned `BizFlow-ERP` folder.
3. **Gradle Sync:**
   - Allow Android Studio to automatically download dependencies and sync the project (Requires Java 11).
4. **Run on Emulator / Device:**
   - Select an Android Virtual Device (AVD running API 24+) from the device selector menu.
   - Click the green **Run 'app'** button (or press `Shift + F10`).

---

## 👤 Author & Maintainer

- **Developer:** [Maira Shahzad](https://github.com/code-with-maira)
- **GitHub Repository:** [https://github.com/code-with-maira/BizFlow-ERP](https://github.com/code-with-maira/BizFlow-ERP)

---

## 📄 License

This project is open-source and available under the [MIT License](LICENSE).
