#!/usr/bin/env bash
# Protege main/master/develop contra commit, push e finish de git flow feitos pelo Claude.

cmd=$(jq -r '.tool_input.command // empty')
br=$(git rev-parse --abbrev-ref HEAD 2>/dev/null)

deny() {
  jq -n --arg r "$1" '{
    hookSpecificOutput: {
      hookEventName: "PreToolUse",
      permissionDecision: "deny",
      permissionDecisionReason: $r
    }
  }'
  exit 0
}

on_protected=false
case "$br" in
  main|master|develop) on_protected=true ;;
esac

# git flow ... finish (merge em main/develop, tag e delete da branch): so o usuario faz
if printf '%s' "$cmd" | grep -Eq '(^|[;&|[:space:]])git[[:space:]]+flow[[:space:]]+[a-z]+[[:space:]]+finish'; then
  deny "git flow finish e proibido para o Claude. O fechamento de feature/release/hotfix e feito manualmente pelo usuario."
fi

# git commit
if printf '%s' "$cmd" | grep -Eq '(^|[;&|[:space:]])git[[:space:]]+commit'; then
  if [ "$on_protected" = true ]; then
    deny "Commits diretos na branch $br sao proibidos. Crie uma branch (feature/..., bugfix/..., hotfix/...) antes de comitar."
  fi
fi

# git merge dentro de branch protegida
if printf '%s' "$cmd" | grep -Eq '(^|[;&|[:space:]])git[[:space:]]+merge'; then
  if [ "$on_protected" = true ]; then
    deny "Merge direto na branch $br e proibido. O fechamento e feito manualmente pelo usuario."
  fi
fi

# git push
if printf '%s' "$cmd" | grep -Eq '(^|[;&|[:space:]])git[[:space:]]+push'; then
  if [ "$on_protected" = true ]; then
    deny "Push a partir da branch $br e proibido. Use uma branch de feature e abra um PR."
  fi
  # destino explicito main/master/develop (ex: HEAD:main, origin develop)
  if printf '%s' "$cmd" | grep -Eq '(^|[[:space:]:/])(main|master|develop)([[:space:]]|$)'; then
    deny "Push com destino a main/master/develop e proibido. Use uma branch de feature e abra um PR."
  fi
  # push sem destino, mas com upstream apontando para uma branch protegida
  upstream=$(git rev-parse --abbrev-ref '@{upstream}' 2>/dev/null)
  case "$upstream" in
    */main|*/master|*/develop)
      deny "O upstream desta branch e $upstream. Push proibido; ajuste o upstream."
      ;;
  esac
fi

exit 0