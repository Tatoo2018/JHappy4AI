# JHappy4AI - Artifacts Repository (gh-pages)

[English](#english) | [日本語](#japanese) | [简体中文](#chinese)

---

<a id="english"></a>
## 🇺🇸 English: Deployment Overview

This branch hosts the Eclipse Update Site artifacts via **GitHub Pages**. The content is automatically managed by the CI/CD pipeline.

### 🔗 Update Site URLs
| Environment | Branch | Eclipse Update Site URL |
| :--- | :--- | :--- |
| **Stable** | `main` | `https://tatoo2018.github.io/JHappy4AI/updatesite/main/` |
| **Development** | `develop` | `https://tatoo2018.github.io/JHappy4AI/updatesite/develop/` |

### 🏗 Directory Structure
Artifacts are separated by their source branch to provide both Stable and Development versions.

* **`updatesite/main/`**: Automated deployment from the **`main`** branch.
* **`updatesite/develop/`**: Automated deployment from the **`develop`** branch.

### 🤖 Automation Logic
1. **Trigger**: A push to `main` or `develop` branch.
2. **Build**: GitHub Actions (`deploy.yml`) builds the feature and plugin JARs.
3. **Push**: The workflow commits and pushes the results to this branch into the corresponding sub-folder.

---

<a id="japanese"></a>
## 🇯🇵 日本語: デプロイの概要

このブランチは、**GitHub Pages** を通じて Eclipse 更新サイトのアーティファクトをホストします。内容は CI/CD パイプラインによって自動的に管理されています。

### 🔗 更新サイト URL
| 環境 | ソースブランチ | Eclipse 更新サイト URL |
| :--- | :--- | :--- |
| **安定版** | `main` | `https://tatoo2018.github.io/JHappy4AI/updatesite/main/` |
| **開発版** | `develop` | `https://tatoo2018.github.io/JHappy4AI/updatesite/develop/` |

### 🏗 ディレクトリ構造
安定版と開発版を使い分けられるよう、ソースブランチごとにディレクトリを分離しています。

* **`updatesite/main/`**: **`main`** ブランチから自動デプロイされます。
* **`updatesite/develop/`**: **`develop`** ブランチから自動デプロイされます。

### 🤖 自動化の仕組み
1. **トリガー**: `main` または `develop` ブランチへのプッシュ。
2. **ビルド**: GitHub Actions (`deploy.yml`) がフィーチャーとプラグインの JAR をビルドします。
3. **プッシュ**: ワークフローがビルド成果物をこのブランチの対応するフォルダへコミットし、プッシュします。

---

<a id="chinese"></a>
## 🇨🇳 简体中文: 部署概览

此分支通过 **GitHub Pages** 托管 Eclipse 更新站点的制品。内容由 CI/CD 流水线自动管理。

### 🔗 更新站点 URL
| 环境 | 源分支 | Eclipse 更新站点 URL |
| :--- | :--- | :--- |
| **稳定版** | `main` | `https://tatoo2018.github.io/JHappy4AI/updatesite/main/` |
| **开发版** | `develop` | `https://tatoo2018.github.io/JHappy4AI/updatesite/develop/` |

### 🏗 目录结构
制品按源分支分开存放，以提供稳定版和开发版。

* **`updatesite/main/`**: 从 **`main`** 分支自动部署。
* **`updatesite/develop/`**: 从 **`develop`** 分支自动部署。

### 🤖 自动化逻辑
1. **触发**: 推送到 `main` 或 `develop` 分支。
2. **构建**: GitHub Actions (`deploy.yml`) 构建 Feature 和 Plugin 的 JAR 文件。
3. **推送**: 工作流将构建产物提交并推送到此分支对应的子目录中。

---

## 🛠 Maintenance (For Maintainers)

> [!CAUTION]
> **Manual Edits**: Do not manually modify files in this branch. They will be overwritten by the next successful CI/CD run.

---
© 2026 Tatoo2018. Managed by GitHub Actions.
