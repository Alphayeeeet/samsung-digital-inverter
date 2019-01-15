/**
 * Copyright (c) 2010-2018 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.samsungdigitalinverter.internal;

import java.util.Collections;
import java.util.Set;

import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.thing.ThingTypeUID;
import org.eclipse.smarthome.core.thing.binding.BaseThingHandlerFactory;
import org.eclipse.smarthome.core.thing.binding.ThingHandler;
import org.eclipse.smarthome.core.thing.binding.ThingHandlerFactory;
import org.openhab.binding.samsungdigitalinverter.SamsungDigitalInverterConstants;
import org.openhab.binding.samsungdigitalinverter.handler.SamsungDigitalInverterHandler;
import org.osgi.service.component.annotations.Component;

/**
 * The {@link SamsungDigitalInverterHandlerFactory} is responsible for creating things and thing
 * handlers.
 *
 * @author Jan Grønlien - Initial contribution
 */
@Component(service = ThingHandlerFactory.class, configurationPid = "binding.SamsungDigitalInverter")
public class SamsungDigitalInverterHandlerFactory extends BaseThingHandlerFactory {

    private static final Set<ThingTypeUID> SUPPORTED_THING_TYPES_UIDS = Collections
            .singleton(SamsungDigitalInverterConstants.THING_TYPE_SDI);
    // private Logger logger = LoggerFactory.getLogger(SamsungDigitalInverterHandlerFactory.class);

    @Override
    public boolean supportsThingType(ThingTypeUID thingTypeUID) {
        return SUPPORTED_THING_TYPES_UIDS.contains(thingTypeUID);
    }

    @Override
    protected ThingHandler createHandler(Thing thing) {
        ThingTypeUID thingTypeUID = thing.getThingTypeUID();

        if (thingTypeUID.equals(SamsungDigitalInverterConstants.THING_TYPE_SDI)) {
            return new SamsungDigitalInverterHandler(thing);
        }

        return null;
    }

}
