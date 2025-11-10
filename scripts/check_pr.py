#!/usr/bin/env python3
import argparse
import sys
from github import Github, GithubException
from github.Auth import Token

def check_pr_size(gh, repo_name: str, pr_number: int):
    """Проверка размера PR по типу ветки"""
    try:
        repo = gh.get_repo(repo_name)
        pr = repo.get_pull(pr_number)
        branch = pr.head.ref.lower()
        lines = pr.additions + pr.deletions

        print(f"\nПроверка размера PR #{pr_number}")
        print(f" Ветка: {branch}")
        print(f" Строк добавлено/удалено: {lines}")

        # Epic — без лимита
        if 'epic/' in branch:
            print(f"Epic PR: {lines} строк → РАЗРЕШЕНО (без лимита)")
            return

        # Лимиты
        limits = {
            'feature': 300,
            'refactor': 400,
            'bugfix': 150
        }
        limit = 300
        for prefix, value in limits.items():
            if prefix in branch:
                limit = value
                break

        if lines > limit:
            print(f"ОШИБКА: {lines} строк > {limit} (лимит для {branch})")
            sys.exit(1)
        else:
            print(f"УСПЕХ: {lines} строк ≤ {limit}")

    except GithubException as e:
        print(f"Ошибка: PR #{pr_number} не найден: {e}")
        sys.exit(1)

def list_team_members(gh, repo_name: str):
    """Вывод команды проекта"""
    print("\nКоманда проекта:")
    try:
        repo = gh.get_repo(repo_name)
        if repo.organization:
            org = repo.organization
            for member in org.get_members():
                teams = [t.name for t in member.get_teams()]
                print(f"  - {member.login} ({member.name or 'No name'}) → команды: {', '.join(teams) if teams else '—'}")
        else:
            print("  (Личный репозиторий — команда не в org)")
            contributors = repo.get_contributors()
            for c in contributors:
                print(f"  - {c.login} (contributor)")
    except Exception as e:
        print(f"  Ошибка получения команды: {e}")

def update_epic_description(gh, repo_name: str, pr_number: int):
    """Обновление описания epic PR в красивом формате"""
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
        print(f" Ветка: {source_branch}")
        print(f" Заголовок: {epic_pr.title}")

        child_prs = repo.get_pulls(state='all', base=source_branch)
        pr_list = list(child_prs)

        description_lines = []
        found = False
        for pr in pr_list:
            if pr.number == pr_number:
                continue

            # Статус
            if pr.merged:
                status = "merged"
            elif pr.state == "closed":
                status = "closed"
            else:
                status = "open"

            # Строка: - [status] #num Title branch
            line = f"- [{status}] #{pr.number} {pr.title} {pr.head.ref}"
            description_lines.append(line)
            print(f" Добавлена: {line}")
            found = True

        if not found:
            description_lines.append("_Нет связанных PR_")

        # Оригинальное описание
        original_body = epic_pr.body or ""
        if original_body and "## Связанные Pull Requests" not in original_body:
            description_lines.extend([
                "", "---", "", "## Оригинальное описание", "", original_body
            ])

        new_description = "\n".join(description_lines)
        epic_pr.edit(body=new_description)

        print(f"\nОписание epic PR #{pr_number} обновлено в красивом формате!")
        print("=" * 60 + "\n")

    except GithubException as e:
        print(f"Ошибка GitHub API: {e}")
        sys.exit(1)
    except Exception as e:
        print(f"Неизвестная ошибка: {e}")
        sys.exit(1)

def main():
    parser = argparse.ArgumentParser(description="CI Helper: check PR, list team, update epic")
    parser.add_argument('--token', required=True, help='GitHub PAT')
    parser.add_argument('--repo', required=True, help='owner/repo')
    parser.add_argument('--pr-number', type=int, help='PR number')

    # Режимы
    group = parser.add_mutually_exclusive_group(required=True)
    group.add_argument('--check-size', action='store_true', help='Check PR size')
    group.add_argument('--list-members', action='store_true', help='List team members')
    group.add_argument('--update-epic', action='store_true', help='Update epic PR description')

    args = parser.parse_args()

    # Проверка: pr-number обязателен для check-size и update-epic
    if (args.check_size or args.update_epic) and not args.pr_number:
        print("Ошибка: --pr-number обязателен для --check-size и --update-epic")
        sys.exit(1)

    gh = Github(auth=Token(args.token))

    if args.check_size:
        check_pr_size(gh, args.repo, args.pr_number)
    elif args.list_members:
        list_team_members(gh, args.repo)
    elif args.update_epic:
        update_epic_description(gh, args.repo, args.pr_number)

if __name__ == '__main__':
    main()