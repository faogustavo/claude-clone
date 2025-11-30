# Claude Clone - Feature Documentation

## Overview
Claude Clone is an IntelliJ Platform plugin that replicates the Claude Code UI and basic features using Compose Multiplatform and the Jewel library for IntelliJ IDEA components.

## Target Features

### 1. Chat Screen with Claude Code Look and Feel

#### 1.1 Main Layout Structure
The application follows a standard IDE tool window layout with the following components:

**Top-Level Structure:**
- **Left Sidebar** - Conversations panel (collapsible)
- **Main Content Area** - Chat interface
- **Bottom Input Area** - Message composition area

#### 1.2 Left Sidebar - Past Conversations Panel
- **Header:**
  - Text: "Past Conversations"
  - Dropdown arrow indicator (chevron down icon)
  - Collapsible/expandable functionality
- **New Conversation Button:**
  - Plus (+) icon button in the top-right corner of the sidebar
  - Creates a new conversation when clicked
- **Conversation List:**
  - List of previous conversations
  - Each item shows conversation preview/title
  - Selected conversation highlighted
  - Scroll support for long lists

#### 1.3 Main Content Area - Chat Interface
- **Header:**
  - Claude Code logo and branding centered at the top
  - Orange star icon (Claude branding)
  - "Claude Code" text
- **Messages Area:**
  - Scrollable container for chat messages
  - Message bubbles for user and assistant
  - Support for different message types:
    - Text messages
    - Code blocks
    - File references
    - System messages
- **Empty State:**
  - Centered Claude icon (orange space invader style icon)
  - Placeholder text when no messages exist
  - Example: "Tired of repeating yourself? Tell Claude to..."

#### 1.4 Bottom Input Area - Message Composition
- **File Context Display:**
  - Show attached/referenced files above the input field
  - File chips with file name and path
  - Remove button (X) for each file reference
- **Input Text Field:**
  - Multi-line text input
  - Placeholder text: (empty initially, shows context when typing)
  - Auto-resize based on content
  - Supports '@' prefix for file autocomplete
- **Options Row:**
  - Left side:
    - Checkbox: "Ask before edits"
    - Pencil/edit icon
  - Right side:
    - Circular progress/thinking indicator (when processing)
    - Slash (/) command menu trigger
    - Send button (arrow up in circle) - orange accent color
    - Enabled only when message is not empty

### 2. File Autocomplete Feature

#### 2.1 Trigger Mechanism
- **Activation:**
  - User types '@' character in the input field
  - Autocomplete popup appears immediately
- **Deactivation:**
  - User selects a file from the list
  - User presses Escape key
  - User clicks outside the popup
  - User deletes the '@' character

#### 2.2 Autocomplete Popup UI
- **Appearance:**
  - Dark background matching the theme
  - Positioned above the input field
  - Floating popup with rounded corners
  - Drop shadow for depth
- **Layout:**
  - Scrollable list of files
  - Maximum height (shows ~10-12 items with scroll)
  - Full width matches input field width

#### 2.3 File List Items
- **Each item displays:**
  - File icon (based on file type)
  - File name (left-aligned, bold/primary text)
  - File path (right-aligned, secondary/muted text color)
- **Item States:**
  - **Default:** Dark background, white text
  - **Hover:** Slightly lighter background
  - **Selected:** Blue background (#0D7ACC or similar), white text
  - **Keyboard Navigation:** Arrow keys to move selection up/down

#### 2.4 File Filtering
- **Search/Filter Logic:**
  - As user types after '@', filter the file list
  - Match against file name (fuzzy matching preferred)
  - Match against file path
  - Show most relevant matches first
- **File Sources:**
  - Project files (all files in the current project)
  - Include common file types (code, config, documentation)
  - Exclude build artifacts, IDE files, etc.
  - Sort by relevance/recency

#### 2.5 Selection Behavior
- **On Selection:**
  - Replace '@' and typed text with file reference
  - Format: File name or '@filename' or custom chip component
  - Popup closes
  - Cursor moves to end of inserted text
  - Space added after reference
- **Multiple Files:**
  - User can add multiple file references in one message
  - Each '@' triggers autocomplete again

## UI Theme and Styling

### Color Scheme (Dark Theme)
Based on the existing `ClaudeCodeTheme.kt`:
- **Background:** `#1A1815` (very dark brown)
- **Secondary Background:** `#3c3c3c` (dark gray)
- **Sidebar:** `#201D18` (darker brown)
- **Primary Accent:** `#E67D22` (orange)
- **Text:** `#E8E6E3` (light cream)
- **Text Cream:** `#F5E6D3` (cream)
- **Secondary Text:** `#C4A584` (muted cream/tan)
- **Selection:** `#30C15F3C` (semi-transparent orange)

### Typography
- **Headers:** Bold, larger font size
- **Body Text:** Regular weight, readable size (13-14px)
- **Secondary Text:** Smaller, muted color for paths and metadata
- **Code:** Monospace font

### Spacing and Layout
- **Padding:** Consistent 8px/12px/16px spacing
- **Borders:** Subtle borders with low opacity
- **Border Radius:** 4px-8px for rounded corners
- **Shadows:** Subtle drop shadows for popups

## Technical Architecture

### Component Structure

```
ClaudeCloneToolWindow
├── ClaudeCloneTheme (theme provider)
└── MainLayout
    ├── SidebarPanel (left)
    │   ├── SidebarHeader
    │   │   ├── "Past Conversations" text
    │   │   ├── Dropdown icon
    │   │   └── New conversation button (+)
    │   └── ConversationList
    │       └── ConversationItem (multiple)
    ├── ChatPanel (center/main)
    │   ├── ChatHeader
    │   │   └── Claude Code branding
    │   ├── MessagesArea (scrollable)
    │   │   ├── EmptyState (when no messages)
    │   │   └── MessageItem (multiple)
    │   └── InputArea
    │       ├── FileContextChips
    │       ├── InputTextField (with autocomplete)
    │       │   └── FileAutocompletePopup
    │       │       └── FileListItem (multiple)
    │       └── InputControls
    │           ├── AskBeforeEditsCheckbox
    │           ├── ThinkingIndicator
    │           ├── CommandMenuButton (/)
    │           └── SendButton
    └── ResizableDivider (between sidebar and chat)
```

### State Management

#### Application State
- **ConversationsState:**
  - List of conversations
  - Selected conversation ID
  - Conversation history
- **ChatState:**
  - Current messages list
  - Input text
  - Attached files
  - Loading/thinking state
- **UIState:**
  - Sidebar collapsed/expanded
  - Autocomplete popup visible
  - File autocomplete query
  - Selected autocomplete item index

#### Data Models
- **Conversation:**
  - id: String
  - title: String
  - createdAt: Timestamp
  - lastMessageAt: Timestamp
- **Message:**
  - id: String
  - conversationId: String
  - content: String
  - role: Enum (User, Assistant, System)
  - timestamp: Timestamp
  - attachedFiles: List<FileReference>
- **FileReference:**
  - path: String
  - name: String
  - type: String (icon/extension)

### Jewel Components to Use

Based on the bundled Jewel modules available:
- **Text** - For all text display
- **TextField** / **InputField** - For message input
- **Button** / **IconButton** - For actions (send, new conversation, etc.)
- **Checkbox** - For "Ask before edits"
- **LazyColumn** - For scrollable lists (messages, conversations, autocomplete)
- **Box, Row, Column** - For layout
- **Surface** - For containers with background
- **Divider** - For separators
- **Icon** - For icons (using custom SVG resources)
- **Popup** - For autocomplete dropdown

### File Access and Project Integration

#### IntelliJ Platform APIs Required
- **Project Service:**
  - Access current project files
  - File indexing for autocomplete
- **VirtualFileManager:**
  - Get project files
  - Filter files by type/location
  - Handle file paths
- **PsiManager (optional):**
  - For more advanced file analysis
  - Get file structure if needed

#### File Autocomplete Logic
1. Listen for '@' character in input field
2. Query project files using VirtualFileManager
3. Filter and rank files based on:
   - Name match
   - Path match
   - Recent usage
   - File type relevance
4. Display filtered results in popup
5. Update list as user types more characters
6. Handle selection and insert reference

## Questions and Clarifications Needed

### 1. Conversation Management
- **Question:** Should conversations be persisted between IDE sessions?
  - If yes, where should they be stored? (IDE settings, local file, etc.)
  - What format? (JSON, XML, etc.)
- **Question:** Should there be a maximum number of conversations to keep?
- **Question:** How should conversation titles be generated? (First message preview, manual naming, etc.)

### 2. Message Functionality
- **Question:** Should the app actually integrate with Claude API or just replicate the UI?
  - If API integration is needed:
    - Which Claude API? (Anthropic API, Claude Code API, etc.)
    - API key management?
    - Streaming responses?
  - If just UI:
    - Mock responses?
    - Static demo data?
- **Question:** Should messages support markdown/code highlighting?
- **Question:** Should there be message actions? (Copy, edit, delete, regenerate)

### 3. File References
- **Question:** When a file is referenced with '@', should it:
  - Just be inserted as text?
  - Be displayed as a chip/badge component?
  - Actually read and include file contents in the message context?
- **Question:** Should there be a limit on file references per message?
- **Question:** Which file types should be included in autocomplete? All files or filtered list?

### 4. Additional Features
- **Question:** Should the sidebar be resizable? (The screenshot shows a divider)
- **Question:** Should conversations be deletable/editable?
- **Question:** Are there any keyboard shortcuts needed?
- **Question:** Should there be a settings/preferences panel?
- **Question:** The screenshot shows a "/" command menu trigger - should this be implemented?
  - If yes, what commands should be available?
- **Question:** Should "Ask before edits" checkbox actually do something, or is it just UI for now?

### 5. Scope and Priority
- **Question:** What is the priority order for features?
  - Phase 1: Basic UI layout only?
  - Phase 2: Autocomplete functionality?
  - Phase 3: Full integration?
- **Question:** Are there any features from the screenshot that should be excluded from the initial implementation?
- **Question:** Should this work across all IntelliJ Platform IDEs (IntelliJ IDEA, WebStorm, PyCharm, etc.)?

### 6. Testing
- **Question:** What level of testing is expected?
  - Unit tests for state management?
  - UI tests for components?
  - Manual testing only?
- **Question:** Should there be test data/mock conversations for development?

## Notes and Assumptions

### Current Assumptions (to be validated):
1. The tool window will be docked (default right side or user preference)
2. Only one conversation can be active at a time
3. File autocomplete will search the entire current project
4. No actual AI integration initially - focus on UI/UX replication
5. Conversation data will be stored in IDE persistent state
6. The UI will adapt to IntelliJ's current theme (light/dark)

### Out of Scope (unless specified otherwise):
1. Actual AI/LLM integration
2. Cloud synchronization of conversations
3. Multi-project file references
4. Advanced code editing/refactoring features
5. Team collaboration features
6. Voice input/output
7. Image/attachment support beyond file references
