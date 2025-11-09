#!/usr/bin/env python3
import argparse
import sys
from github import Github, GithubException
from github.Auth import Token

def check_size(gh, repo_name, pr_number):
    repo = gh.get_repo(repo_name)
    pr = repo.get_pull(pr_number)
    branch = pr.head.ref.lower()
    lines = pr.additions + pr.deletions
    if 'epic/' in branch:
        print(f"Epic PR #{pr_number}: {lines} строк → РАЗРЕШЕНО")
        return
    limit = 300
    if 'feature' in branch: limit = 300
    elif 'refactor' in branch: limit = 400
    elif 'bugfix' in branch: limit = 150
    if lines > limit:
        print(f"ОШИБКА: {lines} > {limit}")
        sys.exit(1)
    print(f"УСПЕХ: {lines} ≤ {limit}")

def list_members(gh, repo_name):
    repo = gh.get_repo(repo_name)
    print("Команда:")
    if repo.organization:
        for m in repo.organization.get_members():
            print(f"  - {m.login}")
    else:
        for c in repo.get_contributors():
            print(f"  - {c.login}")

def update_epic(gh, repo_name, pr_number):
    repo = gh.get_repo(repo_name)
    pr = repo.get_pull(pr_number)
    if 'epic/' not in pr.head.ref.lower():
        return
    child_prs = repo.get_pulls(state='all', base=pr.head.ref)
    lines = [f"# Epic: {pr.title}", "", "## Связанные PR", ""]
    for p in child_prs:
        if p.number == pr_number: continue
        status = "merged" if p.merged else ("closed" if p.state == "closed" else "open")
        lines.append(f"- [{status}] [#{p.number}]({p.html_url})")
    if not any("PR" in l for l in lines): lines.append("_Нет связанных PR_")
    pr.edit(body="\n".join(lines))
    print("Epic PR обновлён!")

def main():
    parser = argparse.ArgumentParser()
    parser.add_argument('--token', required=True)
    parser.add_argument('--repo', required=True)
    parser.add_argument('--pr-number', type=int)
    group = parser.add_mutually_exclusive_group(required=True)
    group.add_argument('--check-size', action='store_true')
    group.add_argument('--list-members', action='store_true')
    group.add_argument('--update-epic', action='store_true')
    args = parser.parse_args()

    if (args.check_size or args.update_epic) and not args.pr_number:
        print("Ошибка: --pr-number обязателен")
        sys.exit(1)

    gh = Github(auth=Token(args.token))
    if args.check_size: check_size(gh, args.repo, args.pr_number)
    elif args.list_members: list_members(gh, args.repo)
    elif args.update_epic: update_epic(gh, args.repo, args.pr_number)

if __name__ == '__main__':
    main()