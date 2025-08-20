# QuickFix Pro - Professional Home Services App

A modern Android application for booking professional home services like plumbing, electrical work, cleaning, and more.

## Features

- **Service Booking**: Browse and book from 12+ professional services
- **Provider Network**: Connect with verified local service providers
- **Modern UI**: Clean, card-based design with gradient backgrounds
- **User Management**: Admin and customer role support
- **Database Integration**: SQLite database with Room persistence library
- **Booking Flow**: Complete booking process from service selection to confirmation

## Services Available

- 🔧 Plumber - Fix pipes, leaks & water issues
- ⚡ Electrician - Electrical repairs & installations
- 🚗 Mechanic - Car & bike repair services
- 🧹 Cleaning - Home & office cleaning
- 🔨 Carpenter - Furniture & wood work
- 🎨 Painter - Interior & exterior painting
- ❄️ AC Repair - AC installation & maintenance
- 📱 Appliance Repair - Fix washing machine, fridge etc
- 🐛 Pest Control - Remove insects & pests
- 🌱 Gardening - Lawn care & plant maintenance
- 🔐 Locksmith - Lock repair & key services
- 💅 Beauty & Spa - Home salon & spa services

## Tech Stack

- **Language**: Kotlin
- **UI**: Material Design Components, CardView, RecyclerView
- **Database**: Room (SQLite)
- **Architecture**: MVVM pattern
- **Build System**: Gradle with KSP
- **Min SDK**: 21 (Android 5.0)
- **Target SDK**: 36

## Installation

1. Clone the repository
```bash
git clone https://github.com/krishanyadav333/quickfix-pro.git
```

2. Open in Android Studio

3. Build and run the project

## App Flow

1. **Login Screen** - User authentication
2. **Services Screen** - Browse available services in grid layout
3. **Providers Screen** - View service providers with contact info
4. **Booking Form** - Enter address and contact details
5. **Success Screen** - Booking confirmation

## Database Schema

### Services Table
- `id` (Primary Key)
- `serviceName` (String)
- `description` (String)

### Providers Table
- `id` (Primary Key)
- `serviceId` (Foreign Key)
- `name` (String)
- `contact` (String)

## Screenshots

*Add your app screenshots here*

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Contact

Krishan Yadav - krishanyadav333@gmail.com

Project Link: [https://github.com/krishanyadav333/quickfix-pro](https://github.com/krishanyadav333/quickfix-pro)