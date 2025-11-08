#!/usr/bin/env python3
import argparse
import sys
from github import Github

def main():
    parser = argparse.ArgumentParser(description="GitHub MR Checker")
    parser.add_argument('--token', required=True, help='GitHub Token')
    parser.add_argument('--project', required=True, help='owner/repo')
    parser.add_argument('--mr-id', type=int, help='PR number')
    parser.add_argument('--mode', choices=['list-users', 'check-size'], required=True)
    args = parser.parse_args()

    gh = Github(args.token)
    repo = gh.get_repo(args.project)

    if args.mode == 'list-users':
        print("Team members:")
        try:
            org = repo.organization
            if org:
                for member in org.get_members():
                    teams = [t.name for t in member.get_teams()]
                    print(f"  - {member.login} ({member.name or 'No name'}) → teams: {teams}")
            else:
                print("  (Personal repo - no org teams)")
                for contrib in repo.get_contributors():
                    print(f"  - {contrib.login}")
        except Exception as e:
            print(f"  Error fetching team: {e}")

    elif args.mode == 'check-size' and args.mr_id:
        pr = repo.get_pull(args.mr_id)
        branch = pr.head.ref.lower()
        total_lines = pr.additions + pr.deletions

        if 'epic' in branch:
            print(f"Epic PR: {total_lines} lines → ALLOWED (no limit)")
            return

        limits = {
            'feature': 300,
            'refactor': 400,
            'bugfix': 150
        }
        limit = 300
        for key in limits:
            if key in branch:
                limit = limits[key]
                break

        if total_lines > limit:
            print(f"FAIL: {total_lines} lines > {limit} (branch: {branch})")
            sys.exit(1)
        else:
            print(f"PASS: {total_lines} ≤ {limit} (branch: {branch})")

if __name__ == '__main__':
    main()