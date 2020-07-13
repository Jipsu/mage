package mage.target.common;

import mage.abilities.Ability;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class TargetTappedPermanentAsYouCast extends TargetPermanent {

    public TargetTappedPermanentAsYouCast() {
    }

    public TargetTappedPermanentAsYouCast(FilterPermanent filter) {
        this.filter = filter;
        this.targetName = filter.getMessage();
    }

    private TargetTappedPermanentAsYouCast(TargetTappedPermanentAsYouCast target) {
        super(target);
    }

    @Override
    public TargetTappedPermanentAsYouCast copy() {
        return new TargetTappedPermanentAsYouCast(this);
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceId, UUID sourceControllerId, Game game) {
        return game.getBattlefield().getAllActivePermanents(getFilter(), game).stream()
                .filter(Permanent::isTapped)
                .map(Permanent::getId)
                .collect(Collectors.toSet());
    }

    @Override
    public boolean canChoose(UUID sourceId, UUID sourceControllerId, Game game) {
        return game.getBattlefield().getAllActivePermanents(getFilter(), game).stream()
                .anyMatch(Permanent::isTapped);
    }

    @Override
    public boolean canTarget(UUID controllerId, UUID id, Ability source, Game game) {
        if (super.canTarget(controllerId, id, source, game)) {
            Permanent permanent = game.getPermanent(id);
            return permanent != null 
                    && permanent.isTapped();
        }
        return false;
    }

}
