#!/usr/bin/env python3
import argparse
import sys
from github import Github, GithubException
from github.Auth import Token

def update_epic_description(gh, repo_name: str, pr_number: int):
    print("\n" + "=" * 60)
    print("ОБНОВЛЕНИЕ ОПИСАНИЯ EPIC PR")
    print("=" * 60)

    try:
        repo = gh.get_repo(repo_name)
        epic_pr = repo.get_pull(pr_number)
        source_branch = epic_pr.head.ref

        if 'epic/' not in source_branch.lower():
            print(f"PR #{pr_number} не epic (ветка: {source_branch})")
            print("=" * 60 + "\n")
            return

        print(f"\nEpic PR: #{pr_number}")
        print(f" Branch: {source_branch}")
        print(f" Title: {epic_pr.title}")

        # Ищем PR, мержащиеся в epic-ветку
        child_prs = repo.get_pulls(state='all', base=source_branch)
        pr_list = list(child_prs)

        description_lines = [
            f"# Epic: {epic_pr.title}",
            "",
            "## Связанные Pull Requests",
            ""
        ]

        found = False
        for pr in pr_list:
            if pr.number == pr_number:
                continue

            if pr.merged:
                status = "merged"
                state_text = "merged"
            elif pr.state == "closed":
                status = "closed"
                state_text = "closed"
            else:
                status = "open"
                state_text = "open"

            pr_url = pr.html_url
            pr_title = pr.title

            description_lines.append(
                f"- [{status}] [#{pr.number} - {pr_title}]({pr_url}) - `{state_text}`"
            )
            print(f" {status} #{pr.number}: {pr_title}")
            found = True

        if not found:
            description_lines.append("_Нет связанных PR_")

        # Оригинальное описание
        original_body = epic_pr.body or ""
        if original_body and "## Связанные Pull Requests" not in original_body:
            description_lines.append("")
            description_lines.append("---")
            description_lines.append("")
            description_lines.append("## Оригинальное описание")
            description_lines.append("")
            description_lines.append(original_body)

        new_description = "\n".join(description_lines)
        epic_pr.edit(body=new_description)

        print(f"\nОписание epic PR #{pr_number} обновлено!")
        print("=" * 60 + "\n")

    except GithubException as e:
        print(f"Ошибка GitHub API: {e}")
        sys.exit(1)
    except Exception as e:
        print(f"Неизвестная ошибка: {e}")
        sys.exit(1)

def main():
    parser = argparse.ArgumentParser(description="Update Epic PR Description")
    parser.add_argument('--token', required=True)
    parser.add_argument('--repo', required=True)
    parser.add_argument('--pr-number', type=int, required=True)
    parser.add_argument('--update-epic', action='store_true')
    args = parser.parse_args()

    if not args.update_epic:
        return

    gh = Github(auth=Token(args.token))
    update_epic_description(gh, args.repo, args.pr_number)

if __name__ == '__main__':
    main()