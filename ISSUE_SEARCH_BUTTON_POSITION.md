# Search button appears on the left side instead of right side

## Problem Description
The search button in the toolbar appears on the left side of the search bar instead of the right side as intended. This affects the user experience and doesn't follow the expected Material Design pattern.

## Current Behavior
- Search button is positioned on the left side of the toolbar
- The `adjustSearchIconPosition()` function attempts to move it to the right but fails silently

## Expected Behavior
- Search button should appear on the right side of the toolbar
- This should follow Material Design guidelines for search functionality

## Root Cause Analysis
1. The search button is not defined in `menu_main.xml`
2. The `adjustSearchIconPosition()` function has several issues:
   - Silent exception handling prevents debugging
   - The search button may not be found at the expected location
   - The delay of 100ms may not be sufficient for UI initialization
   - The gravity setting may not be applied correctly

## Technical Details
- File: `app/src/main/kotlin/org/fossify/calendar/activities/MainActivity.kt`
- Function: `adjustSearchIconPosition()` (lines 427-470)
- The function tries to find the search button using `androidx.appcompat.R.id.search_button`
- It attempts to set `gravity = android.view.Gravity.END` but this may not work

## Proposed Solution
1. Add proper search button definition to the menu
2. Improve the position adjustment logic
3. Add proper error handling and logging
4. Consider using a more reliable approach to position the search button

## Steps to Reproduce
1. Open the calendar app
2. Open the search functionality
3. Observe that the search button appears on the left side

## Environment
- Android version: All supported versions
- App version: Current development version
- Device: All devices

## Priority
Medium - This affects user experience but doesn't break core functionality.