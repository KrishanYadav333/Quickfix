# Admin Service Visibility Feature

## Overview
Admins can now hide and unhide services to control what customers see in the services list.

## Implementation Details

### Database Changes
- Added `isVisible` field to Service entity (default: true)
- Updated database version from 1 to 2
- Added new DAO methods:
  - `getVisibleServices()` - Returns only visible services for customers
  - `updateVisibility(serviceId, isVisible)` - Toggle service visibility

### UI Changes
- Added "Hide/Show" button in admin service cards
- Visual feedback: Hidden services appear with 60% opacity for admins
- Button text changes dynamically: "Hide" for visible services, "Show" for hidden services

### User Experience
- **Customers**: Only see visible services in the services grid
- **Admins**: See all services with visibility controls
- **Visual Feedback**: Hidden services are dimmed for admins
- **Toast Messages**: Confirmation when services are hidden/shown

## Usage
1. Login as admin
2. Navigate to Services screen
3. Each service card shows Edit, Hide/Show, and Delete buttons
4. Click "Hide" to hide a service from customers
5. Click "Show" to make a hidden service visible to customers
6. Hidden services appear dimmed and show "Show" button

## Technical Implementation
- Service model updated with `isVisible: Boolean = true`
- ServiceDao filtering based on user role
- ServiceAdapter handles visibility toggle with database operations
- Real-time updates through Room's Flow-based queries