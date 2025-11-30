# Claude Clone

<!-- Plugin description -->
Claude Clone is an IntelliJ Platform plugin replicating the UI from the Claude AI Agent.
<!-- Plugin description end -->

The main goal for this project is to test how good AI tools can generate plugins for the IntelliJ Platform.
I'll be using Junie with the Claude Sonnet model.

I'll run this in two iterations:

1. Incremental steps with smaller prompts directions
    * Branch: [`gv/incremental`](tree/gv/incremental)
    * The goal will be to give small and incremental feedback to see how far the result can get
2. Bigger prompt directions with a lot of planning
    * Branch: [`gv/planned`](tree/gv/planned)
    * The goal will be to generate a documentation and task list ahead using the Agent and then feeding it back to the
      agent to implement the feature

## Development

This project uses:

- Kotlin
- Gradle with IntelliJ Platform Gradle Plugin
- Jetpack Compose for Desktop UI
- Jewel for Components

### Building

```bash
./gradlew buildPlugin
```

### Running

```bash
./gradlew runIde
```

### Testing

```bash
./gradlew test
```

## Notes

### Incremental

- It often does not find the right methods and composables;
    - It has a hard time to find the correct Jewel components;
    - Context7 does not have Jewel indexed yet;
    - It added Material design a few times before adding that it should use Jewel components in the prompt for each new
      chat;
- It often adds too many "view groups" (wrap the content a row inside, then inside a box then, use a row again);
- It only focus on fixing the problem from the prompt and not plan ahead (like using best practices, etc);
    - It does everything in one file without thinking of it as part of a bigger thing;

### Planned

- The agent has not attempted to add Material at all;
- It generated a task list in multiple steps, which helps to run the agent incrementally;
    - This helps a lot to avoid bloating the context;
- It has not used existing assets and added new ones;
    - This is probably lacking in the agent to know where the assets live, or maybe in the prompt asking to review
      existing assets;
- It thought on the whole
    - Created multiple files
    - Used compose best practices like remember and creating state classes
- Added features that were not included in the initial list (like a side bar with history)

## Videos

| Incremental                              | Planned                              |
|------------------------------------------|--------------------------------------|
| <video controls width="480"><source src="./videos/incremental.mp4" type="video/mp4">Your browser does not support the video tag. <br><a href="./videos/incremental.mp4">Download incremental.mp4</a></video> | <video controls width="480"><source src="./videos/planned.mp4" type="video/mp4">Your browser does not support the video tag. <br><a href="./videos/planned.mp4">Download planned.mp4</a></video> |

## Trademarks

JetBrains, IntelliJ and Junie are registered trademarks of JetBrains s.r.o. 

Claude is a trademark of Anthropic. 

All other product, company and service names mentioned in this README are the property of their respective owners.

> This project is not affiliated with, endorsed by, or sponsored by JetBrains or Anthropic.

## Intended use

This project was created solely for study and testing purposes and is not intended for commercial use or distribution. No commercial usage is authorized or intended.

## License

See [LICENSE](LICENSE) file for details.
