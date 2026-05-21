# AI Interaction Log

## Interaction 1: Basic architecture

- **Prompt:**
  > Act as a Senior Android Developer. I am building a Security Health Dashboard prototype. Use MVVM
  architecture, generate code skelet for following:
  >
  > - ViewModel : manages the state, needs startScan function
  > - DashboardScreen: main page to showcase current device health
  > - ScanScreen: page for scan simulation
  > - SecurityCategory: data model for security check categories.
  >
  > Include a simple NavHost for navigating between dashboard and scan screens. Use StateFlow.
- **Notes:** Assistent generated implementation plan. Added comments.
- **AI Result:** Assitent model provided a complete prototype implementation covering the MVVM
  pattern, including `MainActivity` setup, `SecurityViewModel` with `StateFlow` logic,
  `DashboardScreen`, `ScanScreen`, and `SecurityCategory` models.
- **Commentary:**
    - Assistant generated a full implementation instead of the requested skeleton.
    - Assistent procceded to not create only skeleton, but the prototype. I went through files and
      logic and removed complicated parts that i want to create by my self so i have application
      under control.
- **My changes:**
    - Removed most of the UI components.
    - Removed logic from `SecurityViewModel`
    - Reviewed all of the code logic and understood functionality.

## Interaction 2: Adding fonts and text styles

- **Prompt:**
  > Adjust Type file. Add to the typography readable font, create these predefined font formats:
  >
  > - title: large bold style for titles and sections
  > - label: medium weigth and size for additional info
  > - text: small readable text around 12.sp
  > - status: prominent, high contrast style for status indicators
- **AI Result:** Assistent edited `Type.kt` with styles i asked for, gave it different name, which
  is actually ok, because its mapped to bodySmall etc.
  Assistent added a few more styles. example:
  ```kotlin
  headlineSmall = TextStyle(
      fontFamily = FontFamily.Default,
      fontWeight = FontWeight.Bold,
      fontSize = 24.sp,
      lineHeight = 32.sp,
      letterSpacing = 0.sp
  )
  ```
- **Commentary:**
    - Assistant tried to edit `DashboardScreen` and `ScanScreen`, because i removed lot of his code.
      I had to reject these changes.
    - Assistant did not add new fonts.
- **My changes:**
    - Removed unnecessary styles.
    - Edited the sizes and colors.

## Interaction 3: Creating dashboard components

- **Prompt:**
  > Stop editing things i did not ask to edit. Do not add code i have removed. Use same name as i
  tell you.
  > In DashboardScreen.kt create two composable functions:
  >
  > - CategoryItem: Card that will show security categories. It should show their title, status and
      description
  > - DashboardStatus: Space that will show full device status and NortonButton.
- **AI Result:**
  Generated two composable functions as I asked. Used correct names.
- **Commentary:**
    - Assistant wanted to use different names for typography styles and created an interface for it.
    - As a basic structure it looks good. Now I will style it.
- **My changes:**
    - I removed the typography interface.
    - Added dummy data to test card styles.

### Interaction 4: Creating device health indicator

- **User Prompt:**

  > Complete the TODO in the dashboard status. Indicator should be blured triangle starting from
  right bottom corner. It should glow with status color so it indicates device health.

- **AI Result:**
  In this case Assistant created exact thing I wanted.

  ```kotlin
  @Composable
  fun DashboardStatus(
      status: SecurityStatus,
      onScanClick: () -> Unit
  ) {
      val statusColor = when (status) {
          SecurityStatus.SAFE -> NortonGreen
          SecurityStatus.WARNING -> NortonOrange
          SecurityStatus.DANGER -> NortonRed
          else -> NortonGreen
      }

      Box(modifier = Modifier.fillMaxWidth()) {
          Canvas(
              modifier = Modifier
                  .matchParentSize()
                  .blur(50.dp)
          ) {
              val trianglePath = Path().apply {
                  moveTo(size.width, size.height)
                  lineTo(size.width - 150.dp.toPx(), size.height)
                  lineTo(size.width, size.height - 150.dp.toPx())
                  close()
              }
              drawPath(
                  path = trianglePath,
                  color = statusColor.copy(alpha = 0.8f)
              )
          }

          Column(modifier = Modifier.padding(bottom = 8.dp)) {
              Text(
                  text = "Device Status: ${status.name}",
                  style = MaterialTheme.typography.titleLarge
              )

              Spacer(modifier = Modifier.height(16.dp))

              NortonButton(
                  onClick = onScanClick,
                  text = "Run Scan"
              )
          }
      }
  }
  ```

- **Commentary:**
    - Assistant used blur which is not optimal, but i will keep it.
- **My changes:**
- I had to make indicator use unbounded edgeTreatment so the blur is not cut off.
