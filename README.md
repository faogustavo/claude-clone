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

## Notes

- General notes about the project evolution

## License

See [LICENSE](LICENSE) file for details.
