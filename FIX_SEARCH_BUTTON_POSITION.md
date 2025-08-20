# Fix search button position to appear on the right side

## Summary
This PR fixes the issue where the search button appears on the left side of the toolbar instead of the right side. The current implementation has several issues that prevent the search button from being positioned correctly.

## Changes Made

### 1. Improved Search Button Position Adjustment Logic
- Enhanced the `adjustSearchIconPosition()` function in `MainActivity.kt`
- Added proper error handling and logging instead of silent exception handling
- Increased delay time to ensure UI is fully initialized
- Added multiple fallback methods to find and position the search button

### 2. Added Search Button to Main Menu
- Added search button definition to `menu_main.xml`
- Used proper `app:showAsAction="collapseActionView|always"` for consistent behavior
- Added `app:actionViewClass="androidx.appcompat.widget.SearchView"` for proper search functionality

### 3. Enhanced Error Handling
- Replaced silent exception handling with proper logging
- Added debug information to help identify positioning issues
- Added fallback mechanisms for different Android versions

## Technical Details

### Modified Files:
1. `app/src/main/kotlin/org/fossify/calendar/activities/MainActivity.kt`
   - Enhanced `adjustSearchIconPosition()` function
   - Added proper error handling and logging
   - Improved search button detection logic

2. `app/src/main/res/menu/menu_main.xml`
   - Added search button menu item
   - Configured proper search view behavior

### Key Improvements:
- **Better Timing**: Increased delay from 100ms to 200ms for UI initialization
- **Multiple Detection Methods**: Added several ways to find the search button
- **Proper Gravity Setting**: Ensured gravity is set correctly for right-side positioning
- **Error Logging**: Added proper logging instead of silent exception handling
- **Fallback Mechanisms**: Added multiple approaches to handle different scenarios

## Testing
- [x] Tested on Android 8.0+ devices
- [x] Verified search button appears on the right side
- [x] Confirmed search functionality works correctly
- [x] Tested on different screen sizes and orientations

## Before/After
- **Before**: Search button appears on the left side, positioning logic fails silently
- **After**: Search button appears on the right side with proper error handling and logging

## Related Issue
Fixes #16 - Search button appears on the left side instead of right side

## Checklist
- [x] Code follows the project's coding style
- [x] Self-review of code has been performed
- [x] Code has been tested on multiple devices
- [x] Documentation has been updated if necessary
- [x] No breaking changes introduced