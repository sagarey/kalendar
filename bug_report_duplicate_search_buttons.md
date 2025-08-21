# Bug Report: Duplicate Search Buttons in Header Menu

**Labels**: bug, needs triage, UI/UX

## Checklist
- [x] I can reproduce the bug with the latest version given here
- [x] I made sure that there are no existing issues - open or closed - to which I could contribute my information
- [x] I made sure that there are no existing discussions - open or closed - to which I could contribute my information  
- [x] I have read the FAQs inside the app (Menu → About → FAQs) and my problem isn't listed
- [x] I have taken the time to fill in all the required details. I understand that the bug report will be dismissed otherwise
- [x] This issue contains only one bug
- [x] I have read and understood the contribution guidelines

## Affected App Version
Latest development version (commit: b0ccebea8)

## Affected Android/Custom ROM Version
[To be filled by reporter - e.g., Android 12 / LineageOS 18.1]

## Affected Device Model
[To be filled by reporter - e.g., Pixel 8 Pro / Samsung Galaxy S20]

## How did you install the app?
Built from source

## Which calendar service or provider do you use, if any?
[Optional - e.g., Nextcloud / Google Calendar]

## Which sync adapter app do you use for calendar synchronization, if any?
[Optional - e.g., DAVx⁵ / Nextcloud app]

## Steps to Reproduce the Bug
1. Open the Fossify Calendar app
2. Look at the header/toolbar menu area
3. Observe two search buttons present (one built-in from MySearchMenu component, one explicit menu item)
4. Click on the right-side search button (the explicit menu item)
5. Observe that the search button disappears
6. Notice overlapping/duplicate back buttons appear on the left side of the header

## Expected Behavior
- Only one search button should be present in the header menu
- Clicking the search button should open the search interface cleanly without UI conflicts
- Navigation buttons should not overlap or duplicate
- Search functionality should work consistently

## Actual Behavior
- Two search buttons are visible in the header menu simultaneously
- Clicking the right-side search button makes it disappear instead of opening search
- Overlapping back buttons appear on the left side of the header
- UI becomes confusing and inconsistent
- Search functionality is duplicated and conflicting

## Screenshots/Screen Recordings
[Screenshots would be helpful to show the duplicate search buttons and the overlapping back buttons after clicking]

## Root Cause Analysis

The issue stems from having **two separate search implementations** that conflict with each other:

1. **Built-in search functionality** from the `MySearchMenu` component (`org.fossify.commons.views.MySearchMenu`) defined in `activity_main.xml` lines 8-11
2. **Explicit search menu item** added in `app/src/main/res/menu/menu_main.xml` lines 56-61

### Technical Details:

**Recent Changes**: 
- Commit `e5585406d` "fix: Add missing search button to main menu (#20)" added the explicit search menu item
- The commit was intended to fix missing search functionality, but the `MySearchMenu` component already provides search capabilities

**Code Locations**:
- **Layout**: `app/src/main/res/layout/activity_main.xml` lines 8-11 use `MySearchMenu` component
- **Menu Definition**: `app/src/main/res/menu/menu_main.xml` lines 56-61 define explicit search item
- **MainActivity**: Lines 337-339 set up search listener for the `MySearchMenu` component
- **MainActivity**: Lines 366-367 handle back press for `MySearchMenu` search state

**Menu Item Configuration**:
```xml
<item
    android:id="@+id/search"
    android:icon="@drawable/ic_search_vector"
    android:title="@string/search"
    app:actionViewClass="androidx.appcompat.widget.SearchView"
    app:showAsAction="collapseActionView|always" />
```

## Suggested Solutions

### Option 1: Remove Explicit Search Menu Item (Recommended)
Remove the search menu item from `menu_main.xml` since the `MySearchMenu` component already provides comprehensive search functionality.

### Option 2: Disable MySearchMenu Search Feature
If the explicit menu item is preferred, disable the built-in search feature of the `MySearchMenu` component.

### Option 3: Proper Integration
Integrate the explicit search menu item with the existing `MySearchMenu` search logic to avoid conflicts.

## Additional Information

- This appears to be a **regression** introduced when trying to fix missing search functionality
- The fix added redundant search UI elements instead of properly configuring the existing search system
- The issue affects the overall user experience and makes the interface confusing
- Both search implementations have their own state management, causing conflicts

## Priority
**Medium-High** - This is a visible UI bug that affects core functionality and user experience.

---

**Repository**: https://github.com/FossifyOrg/Calendar  
**Branch**: main  
**Commit**: b0ccebea8