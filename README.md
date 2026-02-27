# 🤖 JHappy AI Exporter (JHappy4AI)

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Eclipse](https://img.shields.io/badge/Eclipse-Plugin-purple.svg)](https://www.eclipse.org/)

*[Read this in [Japanese (日本語)](#日本語) | [Chinese (简体中文)](#chinese)]*

**JHappy AI Exporter** is an Eclipse plugin designed to bridge the gap between your development workspace and AI tools like ChatGPT, Gemini, and Claude. It seamlessly aggregates your selected source code into a single, LLM-optimized text file.

<p align="center">
  <img src="JHappy4AI_eclipsePDEproject/images/image1.jpg" width="600">
</p>

## ✨ Key Features
* **Smart Aggregation:** Merges multiple files and folders into a single Markdown-formatted text file (`.txt`).
* **Token Saving (Filtering):** Exclude unnecessary binaries or build folders (e.g., `.class`, `target/`, `bin/`) via customizable whitelists and blacklists.
* **Context Window Protection:** Set a maximum output size limit (in MB). The export safely truncates with a warning if the limit is reached.
* **Encoding Safety:** Automatically reads files using their respective Eclipse project encodings and exports everything unified in **UTF-8**.
* **Multilingual:** UI and internal Help contents fully support both English and Japanese.



## Installation

To install **JHappy4AI** in your Eclipse IDE, please follow these steps:

1. Copy the following Update Site URL:
   `https://tatoo2018.github.io/JHappy4AI/`
2. In Eclipse, go to **Help** > **Install New Software...**.
3. Click the **Add...** button.
4. Enter `JHappy4AI` in the Name field and paste the URL above into the Location field.
5. Click **Add**, then select **JHappy AI Exporter** from the list.
6. Follow the prompts to complete the installation and restart Eclipse.

> **Note:** Since this is a self-signed plugin, you may see a "Security Warning" during installation. Please click "Install anyway" to proceed.

![Context Menu1](JHappy4AI_eclipsePDEproject/images/image6.jpg)

## 🚀 How to Use
1. Select projects, folders, or files in the **Package Explorer**.
2. Right-click and choose **🤖 Copy Source for AI**.
3. Adjust temporary filters and the MB size limit in the popup dialog.
4. Click **OK** and save the `jhappy_source_context.txt` file.
5. Drag & drop the generated file into your favorite AI chat!

![Context Menu1](JHappy4AI_eclipsePDEproject/images/image5.jpg)

## ⚙️ Configuration
You can change the default extensions and size limits by navigating to:
`Window` > `Preferences` > `JHappy AI Exporter Settings`

![Context Menu1](JHappy4AI_eclipsePDEproject/images/image2.jpg)

## ⚙️ Help Documentation
You can find the user manual within Eclipse:
`Help` > `Help Contents` > `JHappy AI User Guide`

![Context Menu1](JHappy4AI_eclipsePDEproject/images/image7.jpg)

## 📄 License
This project is licensed under the MIT License - see the [LICENSE](JHappy4AI_eclipsePDEproject/LICENSE) file for details.

---

<h1 id="日本語">🤖 JHappy AI Exporter (日本語)</h1>

**JHappy AI Exporter** は、Eclipse上のソースコードをChatGPTやGeminiなどのAIツールに渡す作業を劇的に効率化するプラグインです。選択したプロジェクト資産を、LLM（大規模言語モデル）が最も理解しやすい形式の1つのテキストファイルに集約・出力します。

## ✨ 主な機能
* **スマート結合:** 複数のファイルやフォルダを解析し、Markdown形式で区切られた1つのテキストファイルに出力します。
* **トークン節約（フィルタリング）:** 拡張子（ホワイトリスト/ブラックリスト）やフォルダ名指定により、不要なバイナリやビルド生成物を除外します。
* **コンテキスト長保護（MB制限）:** AIの入力制限に合わせて、出力ファイルの最大サイズ（MB単位）を指定可能。制限到達時は安全に書き込みを中断し、警告を挿入します。
* **文字コード自動統一:** 各ソースファイルの元の文字コード（Shift-JISなど）に関わらず、すべて **UTF-8** に変換して出力し、AI側での文字化けを防ぎます。
* **完全日本語対応:** メニュー、ダイアログ、および内蔵のヘルプシステムはすべて日本語環境に対応しています。

## インストール方法 (Japanese)

Eclipse IDE に **JHappy4AI** をインストールするには、以下の手順に従ってください。

1. 次のアップデートサイトの URL をコピーします：
   `https://tatoo2018.github.io/JHappy4AI/`
2. Eclipse を起動し、メニューの **[ヘルプ] (Help)** > **[新規ソフトウェアのインストール...] (Install New Software...)** を選択します。
3. **[追加...] (Add...)** ボタンをクリックします。
4. 「名前」欄に `JHappy4AI`、「ロケーション」欄に先ほどコピーした URL を入力し、**[追加]** を押します。
5. リストに表示された **JHappy AI Exporter** にチェックを入れます。
6. ウィザードの指示に従い、インストールを完了させて Eclipse を再起動してください。

> **注意:** 本プラグインは署名されていないため、インストール中に「セキュリティ警告」が表示されます。そのまま **[インストールを続行] (Install anyway)** を選択して進めてください。

## 🚀 使い方
1. **パッケージ・エクスプローラー**で、対象のプロジェクトやファイルを選択します。
2. 右クリックメニューから **🤖 AI用にソースを結合して出力** を選択します。
3. ダイアログで今回適用するフィルタや容量制限を確認・調整します。
4. **OK** を押してファイルを保存します。
5. 出力されたテキストファイルを、AIのチャット画面にそのまま貼り付け（ドラッグ＆ドロップ）してください！

## ⚙️ デフォルト設定の変更
毎回同じ設定を入力する手間を省くため、以下のメニューからデフォルト値を変更できます：
`ウィンドウ` ＞ `設定` ＞ `JHappy AI エクスポート設定`

## ⚙️ Help Documentation
Eclipse内の以下のメニューからユーザーガイドを確認できます：
`ヘルプ (Help)` > `ヘルプ目次 (Help Contents)` > `JHappy AI ユーザーガイド`

## 📄 ライセンス
本プロジェクトは MIT License の下で公開されています。詳細は [LICENSE](JHappy4AI_eclipsePDEproject/LICENSE) ファイルをご覧ください。

---

<h1 id="chinese">🤖 JHappy AI Exporter (简体中文)</h1>

**JHappy AI Exporter** 是一款专为 Eclipse 设计的插件，旨在打通开发工作区与 ChatGPT、Gemini 及 Claude 等 AI 工具之间的壁垒。它可以无缝地将您选择的源代码整合为一个经过 LLM（大语言模型）优化的单一文本文件。

## ✨ 主要功能
* **智能聚合:** 将多个文件和文件夹合并为一个 Markdown 格式的文本文件 (`.txt`)。
* **节省 Token (过滤):** 通过可自定义的白名单和黑名单，排除不必要的二进制文件或构建文件夹（如 `.class`, `target/`, `bin/`）。
* **上下文窗口保护:** 设置最大输出大小限制（以 MB 为单位）。如果达到限制，导出将安全截断并发出警告。
* **编码安全:** 自动根据各个 Eclipse 项目的编码读取文件，并统一导出为 **UTF-8** 格式。
* **多语言支持:** UI 及内置帮助内容完全支持英语、日语和中文。

## 安装方法

请按照以下步骤在您的 Eclipse IDE 中安装 **JHappy4AI**:

1. 复制以下更新站点 URL：
   `https://tatoo2018.github.io/JHappy4AI/`
2. 在 Eclipse 中，前往 **Help (帮助)** > **Install New Software... (安装新软件...)**。
3. 点击 **Add... (添加...)** 按钮。
4. 在 Name (名称) 栏输入 `JHappy4AI`，并在 Location (位置) 栏粘贴上述 URL。
5. 点击 **Add (添加)**，然后从列表中勾选 **JHappy AI Exporter**。
6. 按照提示完成安装并重启 Eclipse。

> **注意:** 由于这是一个自签名插件，安装过程中可能会出现“安全警告”。请点击“仍然安装 (Install anyway)”以继续。

## 🚀 如何使用
1. 在 **Package Explorer (包资源管理器)** 中选择项目、文件夹或文件。
2. 右键点击并选择 **🤖 聚合源码以供 AI 使用**。
3. 在弹出的对话框中调整临时过滤器和 MB 大小限制。
4. 点击 **OK** 并保存 `jhappy_source_context.txt` 文件。
5. 将生成的文件拖放到您喜爱的 AI 聊天窗口中！

## ⚙️ 配置
您可以通过以下路径更改默认扩展名和大小限制：
`Window (窗口)` > `Preferences (首选项)` > `JHappy AI Exporter Settings`

## ⚙️ 帮助文档
您可以在 Eclipse 菜单中查看用户指南：
`帮助 (Help)` > `帮助内容 (Help Contents)` > `JHappy AI 用户指南 (JHappy AI User Guide)`

## 📄 开源协议
本项目基于 MIT 协议开源 - 详情请参阅 [LICENSE](JHappy4AI_eclipsePDEproject/LICENSE) 文件。
