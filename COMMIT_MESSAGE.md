fix: Move search button to right side of toolbar

- Enhanced adjustSearchIconPosition() function with proper error handling and logging
- Added search button definition to menu_main.xml with proper SearchView configuration
- Increased delay time from 100ms to 200ms for better UI initialization
- Added multiple fallback methods to find and position search button
- Replaced silent exception handling with proper logging for debugging
- Added three different approaches to detect and position search button:
  1. Direct child view inspection
  2. ID-based search button lookup
  3. Menu item-based search view positioning

This fixes the issue where the search button appeared on the left side
instead of the right side of the toolbar, improving user experience
and following Material Design guidelines.

Fixes: Search button positioning issue
Related: #ISSUE_NUMBER