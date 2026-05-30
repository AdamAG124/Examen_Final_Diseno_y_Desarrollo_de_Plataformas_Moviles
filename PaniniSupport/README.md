# Panini Support — Gestión de Tickets de Soporte (PoC Móvil)

Prueba de concepto móvil para la gestión de tickets de soporte interno
relacionados con la distribución del álbum oficial de la Copa Mundial FIFA 2026
(proveedores, inventario, distribución y logística).

La aplicación centraliza el registro y seguimiento de incidencias que hoy se
gestionan por correo, hojas de cálculo y mensajes informales, reemplazando ese
flujo disperso por un único punto reactivo y mantenible.

> **Alcance:** esta es una PoC. No incluye backend real; los datos son simulados
> (mock). La capa de red queda definida y preparada para una integración futura
> sin necesidad de reorganizar el proyecto.

---

## Funcionalidades

- **Autenticación simulada**: login con validación local (cualquier correo con
  formato válido y contraseña de 4+ caracteres).
- **Listado de tickets** (`LazyColumn`) ordenado por prioridad, mostrando título,
  prioridad, estado, proveedor, categoría y fecha de creación.
- **Detalle del ticket** con información ampliada.
- **Creación de tickets** con formulario validado.
- **Actualización de estado y prioridad** desde el detalle.
- **Actualización reactiva**: la lista se refleja automáticamente al crear o
  modificar un ticket, sin recargar la pantalla.
- **Reordenamiento dinámico**: al cambiar la prioridad, el ticket se reposiciona
  solo (mayor prioridad primero).
- **Feature Flags** para habilitar/deshabilitar funcionalidades de forma centralizada.

---

## Tecnologías

| Área | Tecnología |
|------|-----------|
| Lenguaje | Kotlin |
| UI | Jetpack Compose + Material 3 |
| Arquitectura | MVVM + capa de datos con repositorios |
| Navegación | Navigation Compose |
| Asincronía / reactividad | Coroutines, StateFlow, SharedFlow |
| Capa de red (preparada) | Retrofit + Gson |
| Inyección de dependencias | Manual (Service Locator) |

---

## Arquitectura

El proyecto sigue **MVVM** con separación en capas y organización
**por feature** (cada pantalla agrupa su `Screen` y su `ViewModel`).

```
com.panini.support
├── core/                  # Utilidades transversales
│   ├── result/            # UiState (Loading / Success / Error)
│   ├── event/             # AppEvent + AppEventBus (SharedFlow)
│   └── featureflags/      # FeatureFlags
├── domain/
│   └── model/             # Ticket, Priority, TicketStatus, TicketCategory
├── data/
│   ├── remote/
│   │   ├── dto/request/   # DTOs de entrada (CreateTicketRequest, etc.)
│   │   ├── dto/response/  # DTOs de salida (TicketResponse, etc.)
│   │   ├── TicketApi      # Contrato Retrofit (preparado, no usado aún)
│   │   └── RetrofitClient # Configuración de red (preparada)
│   ├── mapper/            # Conversión DTO <-> dominio
│   ├── mock/              # Datos simulados realistas
│   └── repository/        # ITicketRepository / IAuthRepository + impl
├── di/                    # AppContainer + AppViewModelFactory
└── ui/
    ├── login/
    ├── ticketlist/
    ├── ticketdetail/
    ├── createticket/
    ├── common/            # Componentes reutilizables (chips, selector, etc.)
    ├── navigation/        # Routes + AppNavGraph
    └── theme/
```

### Flujo de datos

```
UI (Compose) → ViewModel → Repository (StateFlow) → ViewModel → UI
```

La UI observa el estado del `ViewModel` con `collectAsStateWithLifecycle()` y
reenvía las acciones del usuario; nunca contiene lógica de negocio. El
repositorio es la **única fuente de verdad** del listado.

### Comunicación reactiva: dos mecanismos

El proyecto distingue deliberadamente entre **estado** y **eventos**:

- **Estado → `StateFlow`** (`TicketRepository.tickets`): la lista de tickets.
  Persiste, tiene valor actual y se reentrega al recomponer. Cualquier mutación
  emite una lista nueva y la UI suscripta se actualiza sola.
- **Eventos → `SharedFlow`** (`AppEventBus`): notificaciones transitorias
  (snackbars al crear/actualizar). Se consumen una sola vez y no se repiten al
  rotar la pantalla.

Esta separación evita el bug clásico de eventos repetidos y mantiene el código
desacoplado: el repositorio emite eventos sin conocer la UI que los muestra.

---

## Decisiones técnicas

- **Sin backend (mock):** acorde al alcance de la PoC. `TicketApi` y
  `RetrofitClient` quedan definidos y coherentes con `/contracts/tickets-api.yaml`;
  el repositorio mock contiene comentarios que indican dónde entrarían las
  llamadas reales. Migrar a backend no requiere reorganizar el proyecto.
- **Inyección de dependencias manual (Service Locator):** `AppContainer` provee
  los repositorios y el bus de eventos como singletons. Se eligió en lugar de
  Hilt para evitar sobreingeniería en una PoC. Como los ViewModels dependen de
  **interfaces** (`ITicketRepository`, `IAuthRepository`), migrar a Hilt más
  adelante no obligaría a modificarlos.
- **Organización por feature:** cada pantalla y su ViewModel viven juntos, lo que
  facilita la mantenibilidad y la incorporación de nuevos desarrolladores.
- **Manejo de estados unificado:** todas las pantallas usan `UiState`
  (sealed interface) para cubrir carga/error/éxito de forma exhaustiva.
- **Ordenamiento por prioridad:** los tickets se ordenan por peso de prioridad
  (descendente) y fecha. El reposicionamiento al cambiar prioridad emerge del
  ordenamiento, sin lógica especial de "mover".

### Feature Flags

Centralizados en `core/featureflags/FeatureFlags.kt`:

| Flag | Efecto |
|------|--------|
| `CREATE_TICKET_ENABLED` | Muestra/oculta el botón de creación de tickets |
| `PRIORITY_UPDATE_ENABLED` | Habilita/deshabilita la edición de prioridad |
| `SHOW_ADMIN_CATEGORIES` | Muestra/oculta categorías administrativas |

Cambiar una funcionalidad requiere modificar una sola línea. La abstracción
permite reemplazar este objeto por una fuente remota (p. ej. Remote Config) sin
tocar los puntos de uso.

---

## Ejecución

### Requisitos
- Android Studio (versión reciente / Ladybug o superior recomendado)
- JDK 17
- SDK mínimo: API 24

### Pasos
1. Clonar el repositorio:
   ```bash
   git clone git@github.com:AdamAG124/Examen_Final_Diseno_y_Desarrollo_de_Plataformas_Moviles.git
   ```
2. Abrir la carpeta del proyecto Android (`app/`) en Android Studio.
3. Esperar a que finalice la sincronización de Gradle (descarga de dependencias).
4. Ejecutar en un emulador o dispositivo físico (Run o Shift + F10).

### Credenciales de prueba
La autenticación es simulada. Ingresar:
- **Correo:** cualquier correo con formato válido (ej. `operador@panini.com`)
- **Contraseña:** cualquier texto de 4 o más caracteres

---

## Estructura del repositorio

```
/
├── app/          # Proyecto Android (Jetpack Compose)
├── contracts/    # API Contracts en formato YAML (OpenAPI 3)
├── docs/         # Documentación técnica y decisiones arquitectónicas
├── video/        # Enlace al video demo (handoff técnico)
└── README.md
```

---

## Consideraciones para otros desarrolladores

- La lógica reactiva nace en `TicketRepository`: toda mutación debe emitir una
  lista nueva (inmutabilidad) para que el `StateFlow` notifique a la UI.
- Para integrar el backend real: implementar `ITicketRepository` consumiendo
  `TicketApi`, ajustar `RetrofitClient.BASE_URL` y cambiar la instancia provista
  en `AppContainer`. Ningún otro punto requiere cambios.
- Los DTOs están separados por intención (request/response) y se mapean a
  dominio en `data/mapper`. El modelo de dominio nunca depende de la red.
