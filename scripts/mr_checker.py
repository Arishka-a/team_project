#!/usr/bin/env python3
import argparse
import sys
from github import Github, GithubException
from github.Auth import Token

def main():
    parser = argparse.ArgumentParser(description="GitHub MR Checker for CI")
    parser.add_argument('--token', required=True, help='GitHub Token (PAT)')
    parser.add_argument('--project', required=True, help='owner/repo')
    parser.add_argument('--mr-id', type=int, help='PR number (for check-size)')
    parser.add_argument('--mode', choices=['check-size', 'list-users'], required=True)
    args = parser.parse_args()

    try:
        gh = Github(auth=Token(args.token))
        repo = gh.get_repo(args.project)
    except GithubException as e:
        print(f"ERROR: Failed to connect to GitHub: {e}")
        sys.exit(1)

    # === MODE: LIST-USERS (оценка 3) ===
    if args.mode == 'list-users':
        print("Команда проекта:")
        try:
            if repo.organization:
                org = repo.organization
                for member in org.get_members():
                    teams = [t.name for t in member.get_teams()]
                    print(f"  - {member.login} ({member.name or 'No name'}) → teams: {', '.join(teams) if teams else '—'}")
            else:
                print("  (Личный репозиторий — команда не в org)")
                contributors = repo.get_contributors()
                for c in contributors:
                    print(f"  - {c.login} (contributor)")
        except Exception as e:
            print(f"  Ошибка получения команды: {e}")
        return

    # === MODE: CHECK-SIZE (оценка 4-5) ===
    if not args.mr_id:
        print("ERROR: --mr-id required for check-size")
        sys.exit(1)

    try:
        pr = repo.get_pull(args.mr_id)
        branch = pr.head.ref.lower()
        lines = pr.additions + pr.deletions

        # Epic — без лимита
        if 'epic/' in branch:
            print(f"Epic PR #{args.mr_id}: {lines} lines → ALLOWED (no limit)")
            return

        # Лимиты по префиксу
        limits = {
            'feature': 300,
            'refactor': 400,
            'bugfix': 150
        }
        limit = 300  # default
        for prefix, value in limits.items():
            if prefix in branch:
                limit = value
                break

        if lines > limit:
            print(f"FAIL: {lines} lines > {limit} (branch: {branch})")
            sys.exit(1)
        else:
            print(f"PASS: {lines} lines ≤ {limit} (branch: {branch})")

    except GithubException as e:
        print(f"ERROR: PR #{args.mr-id} not found or access denied: {e}")
        sys.exit(1)

if __name__ == '__main__':
    main()