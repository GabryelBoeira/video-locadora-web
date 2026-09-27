---
description: Sincroniza configurações do Claude Code entre os dois projetos (video-locadora e notification-service). Use sempre que alterar CLAUDE.md, .claude/settings.json, permissões ou hooks em qualquer um dos projetos para manter consistência global.
---

# Sincronizar Configurações do Claude Code

Este skill garante que alterações nas configurações do Claude sejam refletidas de forma consistente nos dois projetos do workspace.

## Estrutura do workspace

```
video-locadora-web/           ← raiz (CLAUDE.md global)
├── video-locadora/           ← app principal (Maven, porta 8080)
│   └── CLAUDE.md
├── notification-service/     ← microserviço (Gradle, porta 8082)
│   ├── CLAUDE.md
│   └── .claude/settings.json
└── docker/                   ← infraestrutura
```

## Quando executar

- Ao alterar qualquer `CLAUDE.md` (raiz, video-locadora ou notification-service)
- Ao alterar `.claude/settings.json` de qualquer projeto
- Ao adicionar/remover hooks ou permissões
- Ao alterar convenções de código ou build

## O que verificar

1. **Leia os 3 CLAUDE.md** — raiz, video-locadora e notification-service
2. **Identifique a mudança** — o que foi alterado e em qual projeto
3. **Propague se necessário:**
   - Convenções de código → atualizar nos dois projetos + raiz
   - Configuração de build → atualizar apenas no projeto relevante
   - Hooks/permissões → avaliar se aplica ao outro projeto
   - Stack/versões → atualizar raiz + projeto afetado
4. **Valide JSONs** — rodar `jq -e . <arquivo>` em todo settings.json alterado
5. **Mostre ao usuário** um resumo do que foi sincronizado

## Regras

- O CLAUDE.md da raiz é a visão geral — nunca duplicar detalhes específicos de build/run que já estão nos CLAUDE.md dos projetos
- Cada projeto tem seu próprio CLAUDE.md com instruções de build, arquitetura e config específicas
- Hooks e permissões em `.claude/settings.json` são por projeto — só propagar se fizer sentido para ambos
- Sempre preservar configurações existentes ao fazer merge (nunca sobrescrever arrays inteiros)
