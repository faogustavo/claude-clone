# Claude Clone

<!-- Plugin description -->
Claude Clone is an IntelliJ Platform plugin replicating the UI from the Claude AI Agent.
<!-- Plugin description end -->

The main goal for this project is to test how good AI tools can generate plugins for the IntelliJ Platform.
I'll be using Junie with the Claude Sonnet model.

I'll run this in two iterations:

1. Incremental steps with smaller prompts directions
2. Bigger prompt directions with a lot of planning ahead

## Development

This project uses:
- Kotlin
- Gradle with IntelliJ Platform Gradle Plugin
- Jetpack Compose for Desktop UI

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

## Prompts

This was fully build using AI models and IDE Agents. The logos were generated using the Claude web UI.
Everything else was using the Claude Sonnet model in Junie.

## Notes

- The agent does not work well at all with Compose for plugins;
  - It does not find the methods from jewel;
  - It tends to add Material;
  - It does not know best practices for Compose and Kotlin, often doing "basic" code instead of using existing functions;
  - Unfortunately, Context7 does not have Jewel indexed;
  - It often add too many "view groups" (wrap the content a row inside, then inside a box then, use a row again)

## License

See [LICENSE](LICENSE) file for details.
