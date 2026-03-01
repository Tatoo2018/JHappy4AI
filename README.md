# 🤖 JHappy AI Exporter (JHappy4AI) - 🚧 Development Branch

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Eclipse](https://img.shields.io/badge/Eclipse-Plugin-purple.svg)](https://www.eclipse.org/)
[![Branch: develop](https://img.shields.io/badge/branch-develop-orange.svg)]()

> [!WARNING]
> **You are currently viewing the `develop` branch.**
> This branch contains the latest experimental features, bug fixes, and active development work. It may not be fully stable. For the stable release, please switch to the [`main` branch](https://github.com/Tatoo2018/JHappy4AI/tree/main).

*[Read this in [Japanese (日本語)](#日本語) | [Chinese (简体中文)](#chinese)]*

**JHappy AI Exporter** is an Eclipse plugin designed to bridge the gap between your development workspace and AI tools like ChatGPT, Gemini, and Claude. It seamlessly aggregates your selected source code into a single, LLM-optimized text file.

<p align="center">
  <img src="JHappy4AI_eclipsePDEproject/images/image1.jpg" width="600">
</p>

## 🌳 Branching Strategy
This repository uses a structured branching model to separate active development from stable releases.

* **`develop` (Current):** The main working branch. All new features and bug fixes are merged here first. Automatically deployed to the `/develop/` Update Site for testing.
* **`main`:** The stable production branch. Code is merged here only when it is tested and ready for release. Automatically deployed to the `/main/` Update Site.
* **`gh-pages`:** The artifact hosting branch. Managed entirely by GitHub Actions, it stores the compiled `.jar` files and `site.xml` metadata for the Eclipse Update Sites. Do not commit here manually.



## 🛠 CI/CD Build Target
Commits pushed to this `develop` branch are automatically built by GitHub Actions and deployed to the following Update Site URL for testing:
* **Development Build URL:** `https://tatoo2018.github.io/JHappy4AI/updatesite/develop/`

## 📥 Testing Installation

To install the bleeding-edge version from this branch:
1. In Eclipse, go to **Help** > **Install New Software...**.
2. Click **Add...** and paste the **Development Build URL** above.
3. Select **JHappy AI Exporter** (under the TOOL category) from the list.
4. Follow the prompts and restart Eclipse. *(Note: Accept the "Security Warning" for self-signed plugins).*

---
*(Features, How to Use, and Configuration remain the same. See documentation for details.)*
---

<h1 id="日本語">🤖 JHappy AI Exporter - 🚧 開発用ブランチ (develop)</h1>

> [!WARNING]
> **現在表示しているのは `develop` ブランチです。**
> ここには未リリースの最新機能や現在進行中のコードが含まれており、動作が不安定な場合があります。安定版を利用・確認したい場合は [`main` ブランチ](https://github.com/Tatoo2018/JHappy4AI/tree/main) を参照してください。

**JHappy AI Exporter** は、Eclipse上のソースコードをChatGPTやGeminiなどのAIツールに渡す作業を劇的に効率化するプラグインです。

## 🌳 ブランチ構成について
本リポジトリでは、開発環境と安定版を明確に分けるためのブランチ戦略を採用しています。

* **`develop` (現在表示中):** 開発のメインブランチです。新機能や修正はまずここに追加され、テスト用に `/develop/` アップデートサイトへ自動デプロイされます。
* **`main`:** 安定版ブランチです。リリース準備が整った検証済みのコードのみがマージされ、一般ユーザー向けの `/main/` アップデートサイトへ自動デプロイされます。
* **`gh-pages`:** 公開用ホスティングブランチです。GitHub Actions によって完全に自動管理されており、ビルドされたプラグイン本体（`.jar`）や更新インデックスが格納されています。手動での編集は行わないでください。

## 🛠 CI/CD ビルド・デプロイ先
この `develop` ブランチに変更がプッシュされると、GitHub Actions によって自動的にビルドされ、以下のテスト用アップデートサイトにデプロイされます。

* **開発版アップデートサイトURL:** `https://tatoo2018.github.io/JHappy4AI/updatesite/develop/`

## 📥 テスト版のインストール方法
このブランチの最新ビルドをテストするには以下の手順を実行します：
1. Eclipse を起動し、**[ヘルプ]** > **[新規ソフトウェアのインストール...]** を選択します。
2. **[追加...]** をクリックし、上記の**開発版アップデートサイトURL**を入力します。
3. リストの「TOOL」カテゴリー内にある **JHappy AI Exporter** にチェックを入れます。
4. インストールを完了し、Eclipse を再起動してください。（※未署名警告が出た場合は続行してください）

---

<h1 id="chinese">🤖 JHappy AI Exporter - 🚧 开发分支 (develop)</h1>

> [!WARNING]
> **您当前查看的是 `develop` 分支。**
> 此分支包含最新的实验性功能、错误修复以及正在进行的开发工作，可能不够稳定。如需获取稳定版本，请切换至 [`main` 分支](https://github.com/Tatoo2018/JHappy4AI/tree/main)。

**JHappy AI Exporter** 是一款专为 Eclipse 设计的插件，旨在打通开发工作区与 AI 工具之间的壁垒。

## 🌳 分支策略
本仓库采用结构化的分支模型，将活跃开发与稳定发布隔离开来。

* **`develop` (当前):** 主要的开发分支。所有新功能和错误修复都首先合并到这里，并自动部署到 `/develop/` 更新站点以供测试。
* **`main`:** 稳定版分支。只有经过充分测试并准备发布的代码才会合并到这里，并自动部署到面向大众的 `/main/` 更新站点。
* **`gh-pages`:** 制品托管分支。完全由 GitHub Actions 自动管理，存储已编译的 `.jar` 文件和用于 Eclipse 更新站点的元数据。请勿在此分支手动提交。

## 🛠 CI/CD 构建与部署目标
推送到此 `develop` 分支的代码将由 GitHub Actions 自动构建，并部署到以下更新站点以供测试：

* **开发版更新站点 URL:** `https://tatoo2018.github.io/JHappy4AI/updatesite/develop/`

## 📥 测试版安装方法
如需安装此分支的最新测试版本，请执行以下步骤：
1. 在 Eclipse 中，前往 **Help (帮助)** > **Install New Software... (安装新软件...)**。
2. 点击 **Add... (添加...)**，并粘贴上述的**开发版更新站点 URL**。
3. 从列表的 TOOL 分类中勾选 **JHappy AI Exporter**。
4. 完成安装并重启 Eclipse。（※如遇未签名警告，请选择继续安装）

---
© 2026 Tatoo2018. Managed by GitHub Actions.
