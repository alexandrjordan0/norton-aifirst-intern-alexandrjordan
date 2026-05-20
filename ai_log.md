# AI Interaction Log

## Interaction 1: Basic architecture

- **Prompt:**
  Act as a Senior Android Developer. I am building a Security Health Dashboard prototype. Use MVVM architecture, generate code skelet for following:
- ViewModel : manages the state, needs startScan function
- DashboardScreen: main page to showcase current device health
- ScanScreen: page for scan simulation
- SecurityCategory: data model for security check categories.

Include a simple NavHost for navigating between dashboard and scan screens. Use StateFlow.

- **Notes:** Assistent generated implementation plan. Added comments.

-**AI Result:** Assitent model provided a complete prototype implementation covering the MVVM pattern, including `MainActivity` setup, `SecurityViewModel` with `StateFlow` logic, `DashboardScreen`, `ScanScreen`, and `SecurityCategory` models.

- **Commentary:**
  - Assistant generated a full implementation instead of the requested skeleton.
  - Assistent procceded to not create only skeleton, but the prototype. I went through files and logic and removed complicated parts that i want to create by my self so i have application under control.

- **My changes:**
- Removed most of the UI components.
- Removed logic from `SecurityViewModel`
- Reviewed all of the code logic and understood functionality.

## Interaction 2: Adding fonts and text styles

- **Prompt:**
  Adjust Type file. Add to the typography readable font, create these predefined font formats:
- title: large bold style for titles and sections
- label: medium weigth and size for additional info
- text: small readable text around 12.sp
- status: prominent, high contrast style for status indicators

- **AI Result:** Assistent edited `Type.kt` with styles i asked for, gave it different name, and added a few more. example:
  headlineSmall = TextStyle(
  fontFamily = FontFamily.Default,
  fontWeight = FontWeight.Bold,
  fontSize = 24.sp,
  lineHeight = 32.sp,
  letterSpacing = 0.sp
  ),

- **Commentary:**
  - Assistant tried to edit `DashboardScreen` and `ScanScreen`, because i removed lot of his code. I had to reject these changes.
  - Assistant did not add new fonts.

- **My changes:**
- I kept the sizes, but changed names so it orient in the styles better.
