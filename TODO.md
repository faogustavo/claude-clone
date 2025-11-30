# Claude Clone - Implementation Todo List

## Overview
This document provides a step-by-step implementation guide for building the Claude Clone plugin. The tasks are organized into phases to ensure a systematic and incremental development approach.

---

## Phase 1: Foundation and Core UI Layout

### 1.1 Setup Data Models and State Management

**Goal:** Create the foundational data structures and state management for the application.

#### Tasks:

1. **Create Data Models** (`src/main/kotlin/com/faogustavo/claudeclone/model/`)
   - [x] Create `Conversation.kt`:
     - Properties: id (String), title (String), createdAt (Long), lastMessageAt (Long)
     - Add utility methods if needed
   - [x] Create `Message.kt`:
     - Properties: id (String), conversationId (String), content (String), role (MessageRole enum), timestamp (Long), attachedFiles (List<FileReference>)
   - [x] Create `MessageRole.kt` enum:
     - Values: USER, ASSISTANT, SYSTEM
   - [x] Create `FileReference.kt`:
     - Properties: path (String), name (String), extension (String)
     - Add method to get icon based on file type

2. **Create State Classes** (`src/main/kotlin/com/faogustavo/claudeclone/state/`)
   - [x] Create `ConversationsState.kt`:
     - Properties: conversations (List<Conversation>), selectedConversationId (String?)
     - Methods: addConversation, selectConversation, deleteConversation, updateConversation
   - [x] Create `ChatState.kt`:
     - Properties: messages (List<Message>), inputText (String), attachedFiles (List<FileReference>), isLoading (Boolean)
     - Methods: addMessage, updateInputText, addFileReference, removeFileReference, clearInput, setLoading
   - [x] Create `AutocompleteState.kt`:
     - Properties: isVisible (Boolean), query (String), files (List<FileReference>), selectedIndex (Int)
     - Methods: show, hide, updateQuery, updateFiles, selectNext, selectPrevious, getSelected

3. **Create ViewModel/State Holder** (`src/main/kotlin/com/faogustavo/claudeclone/viewmodel/`)
   - [x] Create `ClaudeCloneViewModel.kt`:
     - Use `MutableState` from Compose for reactive state
     - Expose ConversationsState, ChatState, and AutocompleteState
     - Implement business logic methods:
       - createNewConversation()
       - sendMessage(content: String, files: List<FileReference>)
       - loadConversation(id: String)
       - triggerAutocomplete(query: String)
       - selectAutocompleteItem(index: Int)

---

### 1.2 Main Layout Structure

**Goal:** Build the basic layout structure with sidebar and main content area.

#### Tasks:

1. **Update ClaudeClone.kt Main Component**
   - [x] Replace the simple "Hello" text with actual layout structure
   - [x] Wrap content with `ClaudeCloneTheme` provider
   - [x] Create main `Row` layout:
     - Left: Sidebar panel
     - Right: Chat panel
   - [x] Add resizable divider between panels (if implementing resizable sidebar)
   - [x] Set up proper sizing (sidebar width ~250-300dp, chat fills remaining)

2. **Create Layout Components** (`src/main/kotlin/com/faogustavo/claudeclone/components/layout/`)
   - [x] Create `SidebarPanel.kt`:
     - Accept viewModel as parameter
     - Render sidebar with background color from theme
     - Set fixed or minimum width
   - [x] Create `ChatPanel.kt`:
     - Accept viewModel as parameter
     - Render chat area with background color from theme
     - Fill available space

---

### 1.3 Sidebar Components

**Goal:** Implement the left sidebar with conversations list.

#### Tasks:

1. **Create Sidebar Header** (`src/main/kotlin/com/faogustavo/claudeclone/components/sidebar/`)
   - [x] Create `SidebarHeader.kt`:
     - [x] Add "Past Conversations" text (styled with theme colors)
     - [x] Add dropdown icon (chevron down) - use existing icons or create new SVG
     - [x] Add "+" button for new conversation (top-right corner)
     - [x] Implement onClick handler for new conversation button
     - [ ] Optional: Implement collapse/expand functionality for the header

2. **Create Conversation List**
   - [x] Create `ConversationList.kt`:
     - [x] Use `LazyColumn` from Jewel for scrollable list
     - [x] Map conversations from state to UI items
     - [x] Handle empty state (no conversations)
   - [x] Create `ConversationItem.kt`:
     - [x] Display conversation title/preview
     - [x] Display last message timestamp (formatted)
     - [x] Highlight selected conversation (use theme selection color)
     - [x] Implement onClick to select conversation
     - [x] Add hover effect (background color change)

3. **Style Sidebar Components**
   - [x] Apply sidebar background color from theme
   - [x] Set proper padding and spacing (8-12dp)
   - [x] Ensure text uses theme colors (primary and secondary text)
   - [x] Add divider between header and list

---

### 1.4 Chat Panel - Header and Empty State

**Goal:** Create the chat header with branding and empty state UI.

#### Tasks:

1. **Create Chat Header** (`src/main/kotlin/com/faogustavo/claudeclone/components/chat/`)
   - [x] Create `ChatHeader.kt`:
     - [x] Center the Claude Code branding
     - [x] Add Claude icon (orange star/sun icon) - use existing SVG or create new
     - [x] Add "Claude Code" text next to icon
     - [x] Style with theme colors
     - [x] Set proper padding (16-24dp vertical)

2. **Create Empty State Component**
   - [x] Create `EmptyState.kt`:
     - [x] Center content vertically and horizontally
     - [x] Add Claude icon (space invader style) - check existing icons or create new SVG
     - [x] Add placeholder text (e.g., "Tired of repeating yourself? Tell Claude to...")
     - [x] Style with theme secondary text color
     - [x] Make icon larger (64-80dp size)

---

## Phase 2: Chat Messages and Input Area

### 2.1 Messages Area

**Goal:** Display chat messages in a scrollable area.

#### Tasks:

1. **Create Messages Container** (`src/main/kotlin/com/faogustavo/claudeclone/components/chat/`)
   - [x] Create `MessagesArea.kt`:
     - [x] Use `LazyColumn` with reverseLayout for chat-style scrolling
     - [x] Map messages from state to MessageItem components
     - [x] Show EmptyState when no messages
     - [x] Add proper padding and spacing between messages
     - [x] Auto-scroll to bottom when new message arrives

2. **Create Message Components**
   - [x] Create `MessageItem.kt`:
     - [x] Accept Message model as parameter
     - [x] Render different layouts based on role (User vs Assistant)
     - [x] User messages: align right, different background
     - [x] Assistant messages: align left, different background
     - [x] Display message content as text
     - [x] Display timestamp (small, secondary text)
     - [x] Add padding and spacing (8-12dp)
   - [x] Optional: Create `MessageBubble.kt` for styled container
     - [x] Rounded corners (8dp border radius)
     - [x] Background color based on message role
     - [x] Proper padding inside bubble

3. **File References in Messages**
   - [x] Display attached files in messages:
     - [x] Show file icon based on type
     - [x] Show file name
     - [ ] Optional: Make file clickable to open in editor
   - [x] Style file chips with subtle background and border

---

### 2.2 Input Area - Basic Structure

**Goal:** Create the message input field and controls.

#### Tasks:

1. **Create Input Area Container** (`src/main/kotlin/com/faogustavo/claudeclone/components/input/`)
   - [x] Create `InputArea.kt`:
     - [x] Container with background color (slightly different from main background)
     - [x] Arrange components vertically:
       - Top: File context chips (if any files attached)
       - Middle: Input text field
       - Bottom: Controls row
     - [x] Add proper padding (12-16dp)
     - [x] Optional: Add top border/divider

2. **Create Input Text Field**
   - [x] Create `InputTextField.kt`:
     - [x] Use Jewel `TextField` or `InputField` component
     - [x] Multi-line support (set minLines = 1, maxLines = 10 or similar)
     - [x] Bind to viewModel.chatState.inputText
     - [x] Style with theme colors (background, text, border)
     - [x] Placeholder text when empty (optional)
     - [x] Handle text changes and update state

3. **Create Input Controls Row**
   - [x] Create `InputControls.kt`:
     - [x] Row layout with space between
     - [x] Left side:
       - [x] "Ask before edits" checkbox (Jewel Checkbox component)
       - [x] Pencil/edit icon next to checkbox
     - [x] Right side:
       - [x] Thinking indicator (circular progress, show when isLoading)
       - [x] "/" button for command menu (IconButton, disabled for now)
       - [x] Send button (IconButton with arrow up icon, orange accent)
     - [x] Enable send button only when input is not empty
     - [x] Implement onClick handler for send button

4. **Create File Context Chips**
   - [x] Create `FileContextChips.kt`:
     - [x] Display list of attached files as chips
     - [x] Each chip shows:
       - [x] File icon
       - [x] File name
       - [x] "X" close button to remove
     - [x] Horizontal layout with wrapping
     - [x] Style chips with background and border radius
     - [x] Implement remove file functionality

---

## Phase 3: File Autocomplete Feature

### 3.1 File Service and Project Integration

**Goal:** Create service to query and filter project files.

#### Tasks:

1. **Create File Service** (`src/main/kotlin/com/faogustavo/claudeclone/service/`)
   - [x] Create `ProjectFileService.kt`:
     - [x] Accept Project instance in constructor/init
     - [x] Method: `getAllProjectFiles(): List<VirtualFile>`
       - Use VirtualFileManager or ProjectFileIndex
       - Filter out build directories, IDE files, hidden files
       - Include source files, config files, docs
     - [x] Method: `searchFiles(query: String): List<FileReference>`
       - Filter files by name matching query
       - Implement fuzzy matching or simple contains() logic
       - Sort by relevance (name match > path match)
       - Limit results (e.g., top 50-100 matches)
     - [x] Method: `convertToFileReference(virtualFile: VirtualFile): FileReference`
       - Extract name, path, extension
       - Calculate relative path from project root

2. **Integrate File Service with ViewModel**
   - [x] Add ProjectFileService instance to ClaudeCloneViewModel
   - [x] Implement `searchFilesForAutocomplete(query: String)`:
     - [x] Call fileService.searchFiles(query)
     - [x] Update autocompleteState.files with results
     - [x] Set autocompleteState.isVisible = true if results found
   - [x] Handle empty query (show all files or hide autocomplete)

---

### 3.2 Autocomplete Popup UI

**Goal:** Create the file autocomplete popup that appears above input field.

#### Tasks:

1. **Create Autocomplete Popup** (`src/main/kotlin/com/faogustavo/claudeclone/components/autocomplete/`)
   - [x] Create `FileAutocompletePopup.kt`:
     - [x] Use Jewel `Popup` component or custom positioned Box
     - [x] Position above the input field (calculate offset)
     - [x] Set width to match input field width
     - [x] Set max height (show ~10-12 items, then scroll)
     - [x] Dark background with rounded corners (8dp)
     - [x] Add drop shadow for depth
     - [x] Only render when autocompleteState.isVisible = true

2. **Create File List in Popup**
   - [x] Create `FileListItem.kt`:
     - [x] Row layout:
       - Left: File icon (based on file type/extension)
       - Middle: File name (bold, primary text color)
       - Right: File path (secondary text color, smaller)
     - [x] Highlight selected item (blue background #0D7ACC)
     - [x] Hover effect (lighter background)
     - [x] Implement onClick to select file
     - [x] Proper padding (8-12dp)
   - [x] Add file list to popup:
     - [x] Use `LazyColumn` for scrollable list
     - [x] Map autocompleteState.files to FileListItem
     - [x] Pass selectedIndex to highlight current selection

3. **Keyboard Navigation**
   - [x] Implement keyboard event handlers in InputTextField or parent component:
     - [x] Arrow Down: Move selection down (autocompleteState.selectNext())
     - [x] Arrow Up: Move selection up (autocompleteState.selectPrevious())
     - [x] Enter: Select current item (viewModel.selectAutocompleteItem())
     - [x] Escape: Close popup (autocompleteState.hide())
   - [x] Ensure selected item is visible (auto-scroll LazyColumn)

---

### 3.3 Autocomplete Integration with Input

**Goal:** Detect '@' character and trigger autocomplete, handle selection.

#### Tasks:

1. **Implement '@' Detection**
   - [x] In InputTextField, add text change listener:
     - [x] Detect when user types '@' character
     - [x] Find position of '@' in text
     - [x] Extract query after '@' (text from '@' to cursor)
     - [x] Call viewModel.triggerAutocomplete(query)
   - [x] Handle query updates as user continues typing
   - [x] Hide autocomplete when '@' is deleted or cursor moves away

2. **Implement File Selection**
   - [x] In viewModel.selectAutocompleteItem(index: Int):
     - [x] Get selected file from autocompleteState.files[index]
     - [x] Option A (Simple): Replace '@query' with file name in inputText
     - [x] Option B (Chip): Add file to attachedFiles list, remove '@query' from inputText
     - [x] Hide autocomplete popup
     - [x] Move cursor to end of inserted text / after removal
   - [x] Update UI to reflect the change

3. **Handle Edge Cases**
   - [x] Multiple '@' in one message (trigger autocomplete for each)
   - [x] '@' at beginning vs middle of text
   - [x] Clicking outside popup to close
   - [x] Empty search results (show "No files found" message)

---

## Phase 4: Business Logic and Functionality

### 4.1 Conversation Management

**Goal:** Implement creating, loading, and managing conversations.

#### Tasks:

1. **New Conversation**
   - [x] Implement viewModel.createNewConversation():
     - [x] Generate unique ID (UUID or timestamp)
     - [x] Create Conversation object with default title
     - [x] Add to conversationsState.conversations list
     - [x] Select the new conversation (set selectedConversationId)
     - [x] Clear current messages and input
   - [x] Wire up "+" button in SidebarHeader to call this method

2. **Load Conversation**
   - [x] Implement viewModel.loadConversation(id: String):
     - [x] Find conversation in conversationsState by id
     - [x] Load messages for that conversation
     - [x] Update chatState.messages
     - [x] Clear input and attached files
     - [x] Set selectedConversationId
   - [x] Wire up ConversationItem onClick to call this method

3. **Conversation Title Generation**
   - [x] Implement auto-title generation:
     - [x] When first message is sent, generate title from message content
     - [x] Take first 30-50 characters or first sentence
     - [x] Update conversation.title
   - [ ] Optional: Allow manual title editing (future enhancement)

---

### 4.2 Send Message Functionality

**Goal:** Implement sending messages and updating UI.

#### Tasks:

1. **Send User Message**
   - [x] Implement viewModel.sendMessage():
     - [x] Get inputText and attachedFiles from chatState
     - [x] Validate: check if inputText is not empty
     - [x] Create Message object:
       - Generate unique ID
       - Set conversationId to current selected conversation
       - Set role = MessageRole.USER
       - Set content = inputText
       - Set attachedFiles = current attached files list
       - Set timestamp = current time
     - [x] Add message to chatState.messages
     - [x] Update conversation.lastMessageAt
     - [x] Generate title if first message
     - [x] Clear input and attached files (chatState.clearInput())
   - [x] Wire up Send button onClick to call this method

2. **Mock Assistant Response** (UI only, no actual AI)
   - [x] After user message is sent, optionally send mock response:
     - [x] Set chatState.isLoading = true
     - [x] Delay (simulate thinking, e.g., 1-2 seconds)
     - [x] Create mock assistant Message
     - [x] Add to chatState.messages
     - [x] Set chatState.isLoading = false
   - [x] Mock response content: "This is a mock response. AI integration is not implemented yet."
   - [x] Optional: Skip this if just focusing on UI

---

### 4.3 Persistence (Optional but Recommended)

**Goal:** Save and restore conversations between IDE sessions.

#### Tasks:

1. **Create Persistence Service** (`src/main/kotlin/com/faogustavo/claudeclone/service/`)
   - [ ] Create `ConversationPersistenceService.kt`:
     - [ ] Use IntelliJ PropertiesComponent or custom file storage
     - [ ] Method: `saveConversations(conversations: List<Conversation>)`
       - Serialize to JSON (use kotlinx.serialization or Gson)
       - Save to IDE persistent state or local file
     - [ ] Method: `loadConversations(): List<Conversation>`
       - Read from storage
       - Deserialize and return
     - [ ] Method: `saveMessages(conversationId: String, messages: List<Message>)`
     - [ ] Method: `loadMessages(conversationId: String): List<Message>`

2. **Integrate with ViewModel**
   - [ ] On app start (ViewModel init):
     - [ ] Load conversations from persistence
     - [ ] Initialize conversationsState with loaded data
   - [ ] On conversation/message changes:
     - [ ] Save to persistence (debounce to avoid too frequent saves)
   - [ ] Handle migration/versioning if data model changes

---

## Phase 5: Polish and Refinement

### 5.1 Icons and Assets

**Goal:** Ensure all icons are properly created and integrated.

#### Tasks:

1. **Review and Create Missing Icons** (`src/main/resources/icons/`)
   - [x] Claude Code logo icon (orange star/sun) - Already exists
   - [x] Empty state icon (space invader style) - Using ClaudeClone.svg
   - [x] Chevron down icon for sidebar header
   - [x] Plus (+) icon for new conversation
   - [x] Pencil/edit icon for "Ask before edits"
   - [x] Arrow up icon for send button - Already exists as Send.svg
   - [x] Slash (/) icon for command menu - Already exists as Command.svg
   - [x] File type icons (if not using IntelliJ defaults):
     - [x] Using IntelliJ defaults
   - [x] Thinking/loading indicator icon (circular progress or spinner) - Already exists as Thinking.svg

2. **Update Icon References**
   - [x] Add icon references to `ClaudeCloneIcons.kt`
   - [x] Use proper icon loading (Icon.load() or similar)
   - [x] Ensure icons support light/dark themes (separate versions if needed)

---

### 5.2 Theme and Styling Refinement

**Goal:** Ensure consistent theming and polish visual details.

#### Tasks:

1. **Review ClaudeCodeTheme.kt**
   - [ ] Ensure all colors are properly defined for light and dark themes
   - [ ] Add any missing colors (e.g., message bubble backgrounds)
   - [ ] Verify color contrast and accessibility

2. **Apply Theme Consistently**
   - [ ] Review all components and ensure they use theme colors
   - [ ] Check text colors (primary, secondary, accent)
   - [ ] Check background colors (sidebar, chat, input, popups)
   - [ ] Check border and divider colors

3. **Typography and Spacing**
   - [ ] Define consistent font sizes (if not using Jewel defaults)
   - [ ] Apply consistent padding/margin values (8dp, 12dp, 16dp)
   - [ ] Ensure proper line heights and text spacing

4. **Visual Effects**
   - [ ] Add hover effects to interactive elements
   - [ ] Add focus indicators for keyboard navigation
   - [ ] Add subtle shadows to popups and elevated surfaces
   - [ ] Add smooth transitions/animations (optional, keep subtle)

---

### 5.3 Keyboard Shortcuts and Accessibility

**Goal:** Add keyboard support for common actions.

#### Tasks:

1. **Implement Keyboard Shortcuts**
   - [ ] Send message: Cmd/Ctrl + Enter (in input field)
   - [ ] New conversation: Cmd/Ctrl + N (global or when focused)
   - [ ] Focus input field: Cmd/Ctrl + L or similar
   - [ ] Navigate conversations: Up/Down arrows (when sidebar focused)
   - [ ] Autocomplete navigation: Already handled in Phase 3.2

2. **Accessibility**
   - [ ] Ensure all interactive elements are keyboard accessible
   - [ ] Add proper ARIA labels/descriptions (if Compose/Jewel supports)
   - [ ] Test tab navigation order
   - [ ] Ensure sufficient color contrast

---

### 5.4 Error Handling and Edge Cases

**Goal:** Handle errors and edge cases gracefully.

#### Tasks:

1. **Input Validation**
   - [ ] Handle empty messages (send button disabled)
   - [ ] Handle very long messages (limit length or allow with scroll)
   - [ ] Handle special characters in file paths

2. **File Autocomplete Edge Cases**
   - [ ] No project open (disable autocomplete or show message)
   - [ ] No files found (show "No files found" in popup)
   - [ ] File deleted/moved after reference (show error or mark as invalid)

3. **State Edge Cases**
   - [ ] No conversations (show empty state in sidebar)
   - [ ] No selected conversation (disable input or show message)
   - [ ] Loading states (show spinners/progress indicators)

4. **Error Messages**
   - [ ] Show user-friendly error messages for failures
   - [ ] Use IntelliJ notification system for important errors
   - [ ] Log errors for debugging

---

## Phase 6: Testing and Documentation

### 6.1 Testing

**Goal:** Ensure the plugin works correctly.

#### Tasks:

1. **Manual Testing**
   - [ ] Test all user interactions:
     - [ ] Creating new conversation
     - [ ] Selecting conversation
     - [ ] Sending messages
     - [ ] File autocomplete trigger and selection
     - [ ] Attaching and removing files
     - [ ] Keyboard navigation
   - [ ] Test on different platforms (Windows, macOS, Linux if applicable)
   - [ ] Test with light and dark themes
   - [ ] Test with different IntelliJ IDEs (IntelliJ IDEA, WebStorm, etc.)

2. **Unit Tests** (Optional but recommended)
   - [ ] Test ViewModel business logic:
     - [ ] createNewConversation()
     - [ ] sendMessage()
     - [ ] loadConversation()
     - [ ] autocomplete logic
   - [ ] Test State classes methods
   - [ ] Test FileService search and filtering

3. **UI Tests** (Optional)
   - [ ] Use Compose UI testing framework (if available/compatible with IntelliJ plugin)
   - [ ] Test component rendering
   - [ ] Test user interactions

---

### 6.2 Documentation

**Goal:** Document the implementation and usage.

#### Tasks:

1. **Update README.md**
   - [ ] Add screenshots of the plugin in action
   - [ ] Add feature list
   - [ ] Add usage instructions
   - [ ] Add development setup instructions (already exists, update if needed)

2. **Code Documentation**
   - [ ] Add KDoc comments to public APIs:
     - [ ] Data models
     - [ ] ViewModel methods
     - [ ] Service interfaces
   - [ ] Add inline comments for complex logic

3. **Create User Guide** (Optional)
   - [ ] Create USER_GUIDE.md with:
     - [ ] How to open the tool window
     - [ ] How to create conversations
     - [ ] How to use file autocomplete
     - [ ] Keyboard shortcuts
     - [ ] Tips and tricks

---

## Phase 7: Future Enhancements (Optional)

### 7.1 Advanced Features

**Goal:** Add features beyond the initial scope.

#### Potential Enhancements:

1. **Command Menu ("/") Feature**
   - [ ] Implement command palette triggered by "/"
   - [ ] Add commands like /clear, /delete, /export, etc.

2. **Markdown and Code Highlighting**
   - [ ] Parse markdown in messages
   - [ ] Syntax highlighting for code blocks
   - [ ] Support for inline code, bold, italic, etc.

3. **Message Actions**
   - [ ] Copy message button
   - [ ] Edit message (user messages only)
   - [ ] Delete message
   - [ ] Regenerate response (if AI integration)

4. **Conversation Export/Import**
   - [ ] Export conversation to file (JSON, Markdown, etc.)
   - [ ] Import conversation from file

5. **Settings Panel**
   - [ ] Configure theme colors
   - [ ] Configure default behaviors
   - [ ] Configure keyboard shortcuts
   - [ ] API key management (if AI integration)

6. **AI Integration**
   - [ ] Integrate with Claude API (Anthropic)
   - [ ] Stream responses
   - [ ] Handle API errors
   - [ ] Implement rate limiting

7. **Advanced File Features**
   - [ ] Read file contents and include in context
   - [ ] Show file preview on hover in autocomplete
   - [ ] Support for multiple file selection (Ctrl+click)
   - [ ] Drag and drop files to attach

8. **Collaboration Features**
   - [ ] Share conversations with team
   - [ ] Cloud sync (if appropriate)

---

## Summary Checklist

### Phase 1: Foundation and Core UI Layout
- [x] Data models created
- [x] State management implemented
- [x] Main layout structure built
- [x] Sidebar components completed
- [x] Chat header and empty state done

### Phase 2: Chat Messages and Input Area
- [x] Messages display working
- [x] Input field functional
- [x] Send message working
- [x] File context chips displaying

### Phase 3: File Autocomplete Feature
- [x] File service implemented
- [x] Autocomplete popup UI created
- [x] Keyboard navigation working
- [x] '@' detection and selection working

### Phase 4: Business Logic and Functionality
- [x] Conversation management working
- [x] Send message logic complete
- [ ] Persistence implemented (optional)

### Phase 5: Polish and Refinement
- [x] All icons created and integrated
- [x] Theme applied consistently
- [ ] Keyboard shortcuts working
- [ ] Error handling in place

### Phase 6: Testing and Documentation
- [ ] Manual testing complete
- [ ] Unit tests written (optional)
- [ ] Documentation updated

### Phase 7: Future Enhancements (Optional)
- [ ] Advanced features implemented as needed

---

## Notes

- **Incremental Development:** Complete each phase before moving to the next. Test thoroughly at each step.
- **Modular Approach:** Keep components small and focused. Each component should have a single responsibility.
- **State Management:** Use Compose's state management (remember, mutableStateOf) for reactive UI updates.
- **Jewel Components:** Prefer Jewel components over custom implementations for consistency with IntelliJ UI.
- **Theme Integration:** Always use theme colors instead of hardcoded values.
- **Testing:** Test frequently during development, not just at the end.
- **Git Commits:** Commit after completing each major task or sub-phase.
- **Questions:** Refer to FEATURES.md for clarifications needed before implementing certain features.

---

## Dependencies and Prerequisites

### Required Knowledge:
- Kotlin programming
- Jetpack Compose for Desktop
- IntelliJ Platform SDK basics
- Gradle build system

### Required Tools:
- IntelliJ IDEA (or compatible IDE)
- JDK 21
- Gradle (via wrapper)

### External Dependencies:
- Already included in build.gradle.kts:
  - Compose Multiplatform
  - Jewel UI library
  - IntelliJ Platform SDK

### Additional Dependencies (if needed):
- JSON serialization library (kotlinx.serialization or Gson) - for persistence
- Coroutines (kotlinx.coroutines) - for async operations (likely already available)

---

## Estimated Timeline

**Note:** Timeline assumes full-time development. Adjust based on available time and complexity.

- **Phase 1:** 2-3 days
- **Phase 2:** 2-3 days  
- **Phase 3:** 2-3 days
- **Phase 4:** 1-2 days
- **Phase 5:** 1-2 days
- **Phase 6:** 1-2 days
- **Total:** Approximately 9-15 days for core features

Phase 7 (future enhancements) is open-ended and depends on scope.

---

## Getting Started

1. Review FEATURES.md to understand all requirements and answer clarifying questions
2. Set up development environment (already done based on existing project)
3. Start with Phase 1, Task 1.1 - Create data models
4. Work through each task sequentially
5. Test frequently and commit regularly
6. Refer back to the screenshot for visual reference
7. Ask for clarification when needed

**Good luck with the implementation!** 🚀
