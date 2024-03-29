#!/bin/sh
# https://stackoverflow.com/questions/3042437/how-to-change-the-commit-author-for-a-single-commit
git filter-branch --env-filter '

OLD_EMAIL="daniel.ludwig.extern@deutschepost.de"
CORRECT_NAME="laubfall"
CORRECT_EMAIL="ludwig.daniel@gmail.com"

if [ "$GIT_COMMITTER_EMAIL" = "$OLD_EMAIL" ]
then
    export GIT_COMMITTER_NAME="$CORRECT_NAME"
    export GIT_COMMITTER_EMAIL="$CORRECT_EMAIL"
fi
if [ "$GIT_AUTHOR_EMAIL" = "$OLD_EMAIL" ]
then
    export GIT_AUTHOR_NAME="$CORRECT_NAME"
    export GIT_AUTHOR_EMAIL="$CORRECT_EMAIL"
fi
' --tag-name-filter cat -- --branches --tags
